## VisualSorting

VisualSorting is an application that intend to show how different 
sort algorithms work. Moreover you can easily identify which algorithms are more efficient
by running them at the same time. 

Following functionalities are supported by the application

<ul>
<li>visualisation of many different sort algorithms</li>
<li>simultaneous visualisations</li>
<li>step by step - visualisation</li>
<li>adjustable animation speed (ms:ns)</li>
<li>editable sort list</li>
</ul>

## Motivation

VisualSorting has been being mainly developed for educational purposes.

This project has been mainly influenced by the 'sound-of-sorting' from Timo Bingmann. 
For further informations see : https://github.com/bingmann/sound-of-sorting from Timo Bingmann. 

## Installation

The program is written in java, so you can run it on every OS that has a JVM.
It was only tested with java <b>1.7</b> , but it should work just fine on other versions, too. You can
easily launch the program by either double-clicking the downloaded .jar or by using your shell and by executing 
`java -jar visualsorting.jar`.
Currently there is just an installation script for linux, if you want to have additionally a shortcut icon and a menu entry.

The installation script will create 
a program folder and a copy of the executable jar and other files. 
A shortcut icon is also created to provide an easy access to start the application.<br /><br />

First download the .zip file from the repository page.
Afterwards unzip the the downloaded file.
<br /><br /><b>For Linux:</b><br />

  1. open the shell
  2. change directory into unzipped folder with `cd`<br />
  3. Execute install.sh script with `sudo ./install.sh`<br />
  4. Now you can delete the unzipped folder if the script has executed with no errors<br />
  5. The deskton icon is located in `/usr/share/applications/VisualSorting.desktop` and should be now accessable from      your menu.

<br /><br /><b>For Windows:</b><br />

  1. open the unzipped directory<br />
  2. double click on `install.bat`<br />
  3. Now you can delete the unzipped folder<br />
  4. A shortcut on your desktop named `VisualSorting` should now exist



<br /><br /><b>For Mac:</b><br />
  ... comming soon


## Uninstalling
In order to execute those commands you need to be root.<br />

<b>For Linux:</b><br />

 1. `rm -R /home/USER_NAME/.VisualSorting/` replace `USER_NAME` with your user name
 2. `rm -R /opt/VisualSorting/`
 3. `rm /usr/share/applications/VisualSortingBeta.desktop`

<b>For Windows:</b><br />
... comming soon
<br /><br /><b>For Mac:</b><br />
... comming soon


## Contributors

If you find any bugs or have any wishes, please do not hesitate to contact me under 
<b>m.koch189(at)@gmail.com</b>. <br /> <br /> 

## License

This program is licenced under the GNU GPLv3 licence. For more informations please visit : href="http://www.gnu.org/copyleft/gpl.html">http://www.gnu.org/copyleft/gpl.html</a>.

