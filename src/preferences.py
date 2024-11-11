import inspect
import gi
gi.require_version('Gtk', '4.0')
from gi.repository import Gtk
from gi.repository import Adw
import gettext
import locale
import os

def set_language(language_code):


    domain = 'teatime' # This is the domain

    localedir = os.path.join(os.path.dirname(__file__), '../locales')
    mo_file_path = os.path.join(localedir, language_code, 'LC_MESSAGES', f'{domain}.mo')
    if not os.path.exists(mo_file_path):
        print(f"Translation file not found: {mo_file_path}")
        return


    language = gettext.translation(domain, localedir, languages=[language_code])
    language.install()
    global _
    _ = language.gettext


@Gtk.Template(resource_path='/dev/tekofx/TeaTime/preferences.ui')
class PreferencesWindow(Adw.PreferencesWindow):
    __gtype_name__ = 'PreferencesWindow'


    combo_row = Gtk.Template.Child()

    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        print("PreferencesWindow initialized")

        self.combo_row.connect("notify::selected", self.on_combo_row_selected)
        self.language_map = { 0: 'en', 1: 'es' }

    def on_combo_row_selected(self, combo_row, param):
        selected_index = combo_row.get_selected()
        if selected_index in self.language_map:
            language_code = self.language_map[selected_index]
            set_language(language_code)
            print(f"Language changed to: {language_code}")

    def open(self):
        self.present()


