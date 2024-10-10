# window.py
#
# Copyright 2024 Teko
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <https://www.gnu.org/licenses/>.
#
# SPDX-License-Identifier: GPL-3.0-or-later

from typing import List
from gi.repository import Adw
from gi.repository import Gtk, GLib, Gio
from .tea import Tea


@Gtk.Template(resource_path="/dev/tekofx/TeaTime/window.ui")
class TeatimeWindow(Adw.ApplicationWindow):
    __gtype_name__ = "TeatimeWindow"

    label = Gtk.Template.Child()
    box = Gtk.Template.Child()

    def __init__(self, **kwargs):
        super().__init__(**kwargs)

        teas: List[Tea] = [Tea("Té Verde", 180, 90), Tea("Té Negro", 180, 70)]

        for tea in teas:
            minutes, seconds = divmod(tea.time, 60)
            button = Gtk.ToggleButton(label=tea)
            button.connect("clicked", self.on_button_clicked, tea)
            self.box.append(button)

        self.box.set_spacing(10)

    def on_button_clicked(self, widget, tea):
        self.time_left = tea.time
        self.task = Gio.Task.new(self, None, self.on_task_completed)
        self.task.set_task_data(self.time_left, None)
        self.update_label(self.task, tea)  # Llama a update_label inmediatamente
        GLib.timeout_add_seconds(1, self.update_label, self.task, tea)

    def update_label(self, task, tea):

        minutes, seconds = divmod(self.time_left, 60)
        self.label.set_text(f"Tiempo restante: {minutes:02}:{seconds:02}")
        self.time_left -= 1
        if self.time_left == 0:
            task.return_boolean(True)
            return False  # Detiene el temporizador
        return True  # Continúa el temporizador

    def on_task_completed(self, task, result):
        if task.propagate_boolean(result):
            self.label.set_text("Tiempo completado")
