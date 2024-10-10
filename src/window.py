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
from gi.repository import Gtk, GLib, Gio, Notify
from .tea import Tea


@Gtk.Template(resource_path="/dev/tekofx/TeaTime/window.ui")
class TeatimeWindow(Adw.ApplicationWindow):
    __gtype_name__ = "TeatimeWindow"

    label = Gtk.Template.Child()
    box = Gtk.Template.Child()

    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        Notify.init("teatime")

        teas: List[Tea] = [
            Tea("Té Verde", 2, 30, 80),
            Tea("Té Negro", 4, 0, 100),
            Tea("Té Oolong", 4, 0, 100),
            Tea("Té Rojo", 4, 30, 95),
            Tea("Té Blanco", 5, 0, 80),
        ]

        for tea in teas:
            button = Gtk.ToggleButton(label=tea)
            button.connect("clicked", self.on_button_clicked, tea)
            self.box.append(button)

        self.box.set_spacing(10)

    def on_button_clicked(self, widget, tea):

        notification = Notify.Notification.new(
            f"Temporizador de {tea.get_minutes_seconds()}"
        )
        notification.show()

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
            notification = Notify.Notification.new(f"Tu {tea.name} está listo")
            notification.show()
            self.label.set_text(f"Tu {tea.name} está listo")
            return False  # Detiene el temporizador
        return True  # Continúa el temporizador

    def on_task_completed(self, task, result):
        if task.propagate_boolean(result):
            self.label.set_text("Tiempo completado")
