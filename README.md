# 3DJavagame
3D Java game project is group4's school project from spring 2017.
The project is an online 3D Java game.
It uses OpenGL and Light Weight Java Game Library.
The code contains some unneccessary/unused parts and bits due to one of our team members leaving the project mid-way. The code
isn't very well optimized due to the time limit we had. It's also only partially documented.

As of my meritorious features I would like to point out the object mouse picking, which renders each objects' id values on
another renderbuffer and reads back the values from that. Some kind of bounding box mechanism would have been more efficient
but our solution is more accurate.

I also had pretty creative solution for the Client-Server communication. E.g. game.connection.objects.WaiterObject and
game.connection.objects.ReceiveAction interface.

XML-parser and Wavefront-object loader code snippets are straight copy-paste from projects with public free-to-use licenses.
