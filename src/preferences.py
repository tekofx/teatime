import inspect
import gi
gi.require_version('Gtk', '4.0')
from gi.repository import Gtk
from gi.repository import Adw

@Gtk.Template(resource_path='/dev/tekofx/TeaTime/preferences.ui')
class PreferencesWindow(Adw.PreferencesWindow):
    __gtype_name__ = 'PreferencesWindow'


    combo_row = Gtk.Template.Child()

    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        print("PreferencesWindow initialized")

        self.combo_row.connect("notify::selected", self.on_combo_row_selected)

    def on_combo_row_selected(self, combo_row, param):
        print(f"Selected: {combo_row.get_selected_item().get_string()}")

    def open(self):
        self.present()

