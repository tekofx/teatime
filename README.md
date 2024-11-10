# teatime

A description of this project.


## Dev
### Generate pot file
 xgettext --files-from=po/POTFILES --from-code=UTF-8 --output po/teatime.pot
### Generate language translation file
msginit --locale=es --input=po/teatime.pot --output=po/es.po
