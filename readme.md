VisualSorting
=============

THIS PROGRAM IS LICENSED UNDER THE <b>GPL v3</b>. SEE THE <a href="https://github.com/Maurice189/VisualSorting/blob/master/LICENCE.txt">LICENCE</a> FOR MORE INFORMATIONS.


The program is written in java,so you can run it on every OS that has a JVM.
It was only tested on java <b>1.7</b> it hasn't been tested with other versions. Currently there is
just an installation script for linux.A Windows installation script will be published as soon as possible.

<br /><h3>Installation</h3>
For the installation a script is located in the repository folder. For every platform exist
a sepeperate script. You could even compile the source code by yourself, but the application 
needs special program paramters to work correctly. The installation script will create 
a program folder and copy the executable jar and other files like images and a execute script into it. 
As well as a shortcut icon is created to provide a easy way to start the application.<br /><br />

First download the .zip file from the repository page or with the direct download link
<a href ="https://github.com/Maurice189/VisualSorting/archive/master.zip">`https://github.com/Maurice189/VisualSorting/archive/master.zip`</a>. Afterwards unzip the the downloaded file.
<br /><br /><b>For Linux:</b><br />

  1. open the shell
  2. change directory into unzipped folder with `cd`<br />
  3. Execute install.sh script with `sudo ./install.sh`<br />
  4. Now you can delete the unzipped folder if the script executed with no error<br />
  5. The deskton icon is located in `/usr/share/applications/VisualSorting.desktop` and should be now accessable from your menu

<br /><br /><b>For Windows:</b><br />

  1. open the unzipped directory<br />
  2. double click on `install.bat`<br />
  3. Now you can delete the unzipped folder<br />
  4. A shortcut on your desktop named `VisualSorting` should now exist



<br /><br /><b>For Mac:</b><br />

  1. open the shell
  2. change directory into unzipped folder with `cd`<br />
  ... comming soon


<br /><h3>Deinstallation</h3>
In order to execute those commands you need to be root.<br />

<b>For Linux:</b><br />

 1. `rm -R /home/USER_NAME/.VisualSorting/` replace `USER_NAME` with your user name
 2. `rm -R /opt/VisualSorting/`
 3. `rm /usr/share/applications/VisualSortingBeta.desktop`

<b>For Windows:</b><br />
... comming soon
<br /><br /><b>For Mac:</b><br />
... comming soon

<br /><h3>About VisualSorting</h3>

<b>VisualSorting</b> has been being mainly developed for educational purposes.<br /> 
Following functionalities are supported by the application

<ul>
<li>visualisation heapsort,bubblesort,quicksort,combsort,gnomesort,mergesort,shakersort (more will be added soon)</li>
<li>simultaneous visualisations</li>
<li>step by step - visualisation</li>
<li>adjustable animation speed (ms:ns)</li>
<li>editable sort list</li>
</ul>


The sceenshot below shows the animation process. This is a common approach of visualize sort algorithms. It has been mainly influenced this project https://github.com/bingmann/sound-of-sorting from Timo Bingmann. 

<p><img src="https://raw.githubusercontent.com/Maurice189/VisualSorting/master/screenshot_VisualSorting.png" alt="screenshot"></img></p>

Currently (June '14) there are a few bugs and I have to adapt some things. If you find any bugs or have any wishes, please do not hesitate to contact me under 
<b>m.koch189(at)@gmail.com</b>. <br /> <br /> 
Thanks,<br />
Maurice K.
