# window.py
#
# Copyright 2024 Teko
#


import inspect
from typing import List
from gi.repository import Adw
from gi.repository import Gtk, GLib, Gio, Notify, GdkPixbuf
from .tea import Tea


@Gtk.Template(resource_path="/dev/tekofx/TeaTime/tea_button.ui")
class TeaButton(Gtk.ToggleButton):
    __gtype_name__ = "TeaButton"

    teaIcon = Gtk.Template.Child()
    teaLabel = Gtk.Template.Child()
    teaInfoLabel = Gtk.Template.Child()

    def __init__(self, tea:Tea, timerLabel:Gtk.Label):
        super().__init__()
        Notify.init("Tea Time")
        self.tea=tea
        self.teaIcon.set_from_icon_name("tea-symbolic")
        self.teaLabel.set_markup(f"<span size='large' foreground='{tea.color}'>{tea.name}</span>")
        self.teaInfoLabel.set_markup(f"<span size='large' foreground='{tea.color}'>{tea.time} {tea.temperature}</span>")
        self.timerLabel=timerLabel

        self.connect("clicked", self.on_button_clicked)


    def on_button_clicked(self, widget):

        notification = Notify.Notification.new(f"Temporizador de {self.tea.time}")
        notification.show()

        self.time_left = self.tea.time_seconds
        self.task = Gio.Task.new(self, None, self.on_task_completed)
        self.task.set_task_data(self.time_left, None)
        self.update_label(self.task, widget)  # Llama a update_label inmediatamente
        GLib.timeout_add_seconds(1, self.update_label, self.task, widget)

    def update_label(self, task, widget):

        minutes, seconds = divmod(self.time_left, 60)
        self.timerLabel.set_text(f"{minutes}:{seconds:02}")
        self.time_left -= 1
        if self.time_left == 0:
            task.return_boolean(True)
            notification = Notify.Notification.new(f"Tu {self.tea.name} está listo")
            notification.show()
            widget.set_active(False)
            self.timerLabel.set_text(f"0:00")
            return False  # Detiene el temporizador
        return True  # Continúa el temporizador

    def on_task_completed(self, task, result):
        if task.propagate_boolean(result):
            self.timerLabel.set_text("Tiempo completado")

