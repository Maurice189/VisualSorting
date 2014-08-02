#!/bin/bash

stuser="maurice"
cpath=$PWD


cd src
javac algorithms/*.java main/*.java dialogs/*.java 
jar -cvfm $cpath/VisualSorting.jar $cpath/manifest.mf *


mkdir /home/$stuser/.VisualSorting/
sudo chown -cR $stuser /home/$stuser/.VisualSorting/

sudo mkdir /opt/VisualSorting/
sudo cp $cpath/VisualSortingIcon.png /opt/VisualSorting/VisualSortingIcon.png
sudo cp $cpath/VisualSorting.jar /opt/VisualSorting/VisualSorting.jar
sudo cp $cpath/splash.gif /opt/VisualSorting/splash.gif


sudo echo "
#!/bin/bash
java -jar -splash:/opt/VisualSorting/splash.gif -Xms1024m -Xmx1024m /opt/VisualSorting/VisualSorting.jar -configdir:/home/"$stuser"/.VisualSorting/
" > /opt/VisualSorting/VisualSorting.sh


sudo chmod 755 -R /opt/VisualSorting/



sudo echo "[Desktop Entry]
Type=Application
Terminal=true
Name=VisualSorting
GenericName=Sorting Algoritms Animation
Categories=Education;Science;Java
Comment=Visualize Several Sorting Algoritms
Icon=/opt/VisualSorting/VisualSortingIcon.png
Exec=/opt/VisualSorting/VisualSorting.sh
" > /usr/share/applications/VisualSorting.desktop

