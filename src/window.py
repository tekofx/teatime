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
from gi.repository import Gtk, GLib, Gio, Notify, GdkPixbuf
from .tea import Tea
from .tea_button import TeaButton
import gettext
from .preferences import _


@Gtk.Template(resource_path="/dev/tekofx/TeaTime/window.ui")
class TeatimeWindow(Adw.ApplicationWindow):
    __gtype_name__ = "TeatimeWindow"

    flowBox = Gtk.Template.Child()
    box = Gtk.Template.Child()
    timerLabel = Gtk.Template.Child()

    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        self.set_title("Tea Time")
        self.current_active_button = None
        Notify.init("Tea Time")
        teas: List[Tea] = [
            Tea(_("Green Tea"), 2, 30, 80, "#32a852"),
            Tea(_("Black Tea"), 4, 0, 100, "#575958"),
            Tea(_("Oolong Tea"), 4, 0, 100, "#779bbf"),
            Tea(_("Red Tea"), 4, 30, 95, "#bf7791"),
            Tea(_("Rooibos"), 3, 0, 100, "#bf7791"),
            Tea(_("White Tea"), 5, 0, 80, "#f7f7f7"),
            Tea(_("Mint Tea"), 4, 0, 80, "#f7f7f7"),
        ]

        for tea in teas:
            teaButton = TeaButton(tea, self.timerLabel, self.current_active_button)
            self.flowBox.append(teaButton)
