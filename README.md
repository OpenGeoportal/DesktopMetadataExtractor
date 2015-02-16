Metadata Extractor GUI
======================

An application to extract metadata from geospatial data files and generate ISO19115 metadata XML documents for a metadata catalog.

Requirements
------------

- Ant is required to build the application.

Build
-----

To create an exe file:

```
$ ant exe
```

To create a jar file:

```
$ ant dist
```

Applications are created in the dist folder.


**TODO:** Describe how to update the talend process for the GUI.

Create the installer
--------------------

To create the exe and jar installers:

```
$ cd installer
$ ant
```

The installers are created in the folder ```build```. For now doesn't include gdal, that is required to be installed manually.

