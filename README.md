# Blocks SQLite Plugin

This plugin uses an SQLite file to store your tasks. By default, the SQLite file
will be created in the current working directory. An option to change this will
be made available once plugin specific settings access is enabled.

# How do I use it?

> **Heads up!** Blocks is still in development. These instructions might not be
> applicable yet!

1. Navigate to the folder where Blocks is installed.
2. Create a new folder `06d55f64-a1a8-4b3b-a52c-9c9a97760015`. In this folder,
download the JAR and rename it to `06d55f64-a1a8-4b3b-a52c-9c9a97760015`.
Alternatively, name the folder `blocks-sqlite-plugin` and leave the JAR name as
is.
3. Edit plugins.ini to have the following entries **CONSECUTIVELY**:
> **WARNING!** If these entries are not consecutive, the application might not
> load the plugin.
```
plugin-uuid = 06d55f64-a1a8-4b3b-a52c-9c9a97760015
plugin-name = blocks-sqlite-plugin
```
Alternatively, set the plugin loading mode to discovery mode. This mode will
sweep through the plugins directory and will not load invalid plugins.
