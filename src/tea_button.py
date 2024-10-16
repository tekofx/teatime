# window.py
#
# Copyright 2024 Teko
#


from gi.repository import Adw
from gi.repository import Gtk, GLib, Gio, Notify
from .tea import Tea


@Gtk.Template(resource_path="/dev/tekofx/TeaTime/tea_button.ui")
class TeaButton(Gtk.ToggleButton):
    __gtype_name__ = "TeaButton"

    teaIcon = Gtk.Template.Child()
    teaLabel = Gtk.Template.Child()
    teaInfoLabel = Gtk.Template.Child()

    def __init__(self, tea: Tea, timerLabel: Gtk.Label):
        super().__init__()
        Notify.init("Tea Time")
        self.tea = tea
        self.teaIcon.set_from_icon_name("tea-symbolic")
        self.teaLabel.set_markup(
            f"<span size='large' foreground='{tea.color}'>{tea.name}</span>"
        )
        self.teaInfoLabel.set_markup(
            f"<span size='large' foreground='{tea.color}'>{tea.time}   {tea.temperature}ºC</span>"
        )
        self.timerLabel = timerLabel

        self.connect("clicked", self.on_button_clicked)
        target = Adw.CallbackAnimationTarget.new(self.animate_opacity)
        spring_params = Adw.SpringParams(0, 0.1, 1)
        self.animation = Adw.SpringAnimation.new(
            self.timerLabel, 0.5, 1, spring_params, target
        )

    def animate_opacity(self, value):
        self.timerLabel.set_opacity(value)

    def on_button_clicked(self, widget):
        self.animation.play()

        self.time_left = self.tea.time_seconds
        self.task = Gio.Task.new(self, None, self.on_task_completed)
        self.task.set_task_data(self.time_left, None)
        self.update_label(self.task, widget)  # Llama a update_label inmediatamente
        GLib.timeout_add_seconds(1, self.update_label, self.task, widget)

    def update_label(self, task, widget):
        minutes, seconds = divmod(self.time_left, 60)
        self.timerLabel.set_text(f"{minutes}:{seconds:02}")

        if self.time_left == 0:
            task.return_boolean(True)
            notification = Notify.Notification.new(f"Tu {self.tea.name} está listo")
            notification.show()
            widget.set_active(False)
            self.timerLabel.set_text(f"0:00")

            return False  # Detiene el temporizador
        self.time_left -= 1
        return True  # Continúa el temporizador

    def on_task_completed(self, task, result):
        self.animation.pause()
        self.timerLabel.set_opacity(1)
