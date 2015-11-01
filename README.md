# JImageStats
A simple and elegant tool for visualizing and tracking your camera and lens usage.
You can get the executable jar file in \dist directory.

Upcoming improvements :
- Support for 35mm focal length (on camera that doesn't that that in EXIF info)
- Better graph representation
- Sortable table

Experimental improvements :
- Fix the memory leak issue caused by the image tooltip. Current temporary alleviation is to trigger Java's Garbage Collection after the image tooltip disappears.