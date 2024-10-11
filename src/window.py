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

import inspect
from typing import List
from gi.repository import Adw
from gi.repository import Gtk, GLib, Gio, Notify
from .tea import Tea


@Gtk.Template(resource_path="/dev/tekofx/TeaTime/window.ui")
class TeatimeWindow(Adw.ApplicationWindow):
    __gtype_name__ = "TeatimeWindow"
    box2 = Gtk.Template.Child()
    box = Gtk.Template.Child()

    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        self.set_title("Tea Time")
        Notify.init("Tea Time")

        teas: List[Tea] = [
            Tea("Té Verde", 0, 3, 80, "#32a852"),
            Tea("Té Negro", 4, 0, 100, "#575958"),
            Tea("Té Oolong", 4, 0, 100, "#779bbf"),
            Tea("Té Rojo", 4, 30, 95, "#bf7791"),
            Tea("Té Blanco", 5, 0, 80, "#f7f7f7"),
        ]

        for tea in teas:
            button = Gtk.ToggleButton()
            box = Gtk.Box(orientation=Gtk.Orientation.VERTICAL)

            # Añadir icono
            icon = Gtk.Image.new_from_icon_name("tea-symbolic")
            icon.set_icon_size(Gtk.IconSize.LARGE)
            box.append(icon)

            # Añadir texto con formato
            label = Gtk.Label()

            markup = f"<span size='large' foreground='{tea.color}'>{tea.name}</span>\n<span>{tea.time} {tea.temperature}ºC</span>"
            label.set_markup(markup)
            box.append(label)

            button.set_child(box)

            button.connect("clicked", self.on_button_clicked, tea)
            self.box.append(button)



    def on_button_clicked(self, widget, tea):

        notification = Notify.Notification.new(f"Temporizador de {tea.time}")
        notification.show()

        self.time_left = tea.time_seconds
        self.task = Gio.Task.new(self, None, self.on_task_completed)
        self.task.set_task_data(self.time_left, None)
        self.update_label(self.task, widget, tea)  # Llama a update_label inmediatamente
        GLib.timeout_add_seconds(1, self.update_label, self.task, widget, tea)

    def update_label(self, task, widget, tea):

        minutes, seconds = divmod(self.time_left, 60)
        self.label.set_text(f"Tiempo restante: {minutes:02}:{seconds:02}")
        self.time_left -= 1
        if self.time_left == 0:
            task.return_boolean(True)
            notification = Notify.Notification.new(f"Tu {tea.name} está listo")
            notification.show()
            widget.set_active(False)
            self.label.set_text(f"Tu {tea.name} está listo")
            return False  # Detiene el temporizador
        return True  # Continúa el temporizador

    def on_task_completed(self, task, result):
        if task.propagate_boolean(result):
            self.label.set_text("Tiempo completado")
