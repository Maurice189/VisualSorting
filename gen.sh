#!/bin/bash

#
#  _   ___               __  ____         __  _          
# | | / (_)__ __ _____ _/ / / __/__  ____/ /_(_)__  ___ _
# | |/ / (_-</ // / _ `/ / _\ \/ _ \/ __/ __/ / _ \/ _ `/
# |___/_/___/\_,_/\_,_/_/ /___/\___/_/  \__/_/_//_/\_, / 
#                                     
#
# Visualsorting
# Copyright (C) 2014  Maurice Koch
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
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#
#


# RUN THE SCRIPT IN YOUR CONSOLE WITH THE COMMAND: 'sh install.sh'
#
# IF YOUR NOT CURRENTLY IN THE DIRECTORY WHERE 'install.sh' IS LOCATED, YOU HAVE TO CHANGE DIRECTORY WITH 
# 'cd /DIRECTORY_PATH/'
  

# PLEASE REMEMBER FOLLOWING HINTS:
#  
#     - DO RUN THE SCRIPT IN SUPER PRIVELEGE MODE, OTHERWISE THE SCRIPT WON'T OPERATE PROPERLY
#


if (( $EUID != 0 )); then
    echo "Please run as root"
    exit
fi

USER_HOME=$(getent passwd $SUDO_USER | cut -d: -f6)
FOLDERPATH=$PWD


cd src
javac algorithms/*.java main/*.java dialogs/*.java 
jar -cvfm $FOLDERPATH/VisualSorting.jar $FOLDERPATH/manifest.mf *


mkdir $USER_HOME/.VisualSorting/
sudo chown -cR $SUDO_USER $USER_HOME/.VisualSorting/

sudo mkdir /opt/VisualSorting/
sudo cp $FOLDERPATH/VisualSortingIcon.png /opt/VisualSorting/VisualSortingIcon.png
sudo cp $FOLDERPATH/VisualSorting.jar /opt/VisualSorting/VisualSorting.jar
sudo cp $FOLDERPATH/splash.gif /opt/VisualSorting/splash.gif
sudo cp $FOLDERPATH/LICENCE.txt /opt/VisualSorting/LICENCE.txt


sudo echo "
#!/bin/bash
java -jar -splash:/opt/VisualSorting/splash.gif -Xms1024m -Xmx1024m /opt/VisualSorting/VisualSorting.jar -configdir:"$USER_HOME"/.VisualSorting/
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

