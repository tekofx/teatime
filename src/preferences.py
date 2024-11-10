import gi
gi.require_version('Gtk', '4.0')
from gi.repository import Gtk
from gi.repository import Adw

@Gtk.Template(resource_path='/dev/tekofx/TeaTime/preferences.ui')
class PreferencesWindow(Adw.PreferencesWindow):
    __gtype_name__ = 'PreferencesWindow'

    preferences_page = Gtk.Template.Child()
    preferences_group = Gtk.Template.Child()
    action_row = Gtk.Template.Child()
    combo_row = Gtk.Template.Child()

    def __init__(self, **kwargs):
        super().__init__(**kwargs)

    def open(self):
        self.present()

