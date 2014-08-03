@
@ _   ___               __  ____         __  _          
@| | / (_)__ __ _____ _/ / / __/__  ____/ /_(_)__  ___ _
@| |/ / (_-</ // / _ `/ / _\ \/ _ \/ __/ __/ / _ \/ _ `/
@|___/_/___/\_,_/\_,_/_/ /___/\___/_/  \__/_/_//_/\_, / 
@                                    
@
@Visualsorting
@Copyright (C) 2014  Maurice Koch
@
@This program is free software: you can redistribute it and/or modify
@it under the terms of the GNU General Public License as published by
@the Free Software Foundation, either version 3 of the License, or
@(at your option) any later version.
@
@
@This program is distributed in the hope that it will be useful,
@but WITHOUT ANY WARRANTY; without even the implied warranty of
@MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
@GNU General Public License for more details.
@
@You should have received a copy of the GNU General Public License
@along with this program.  If not, see <http://www.gnu.org/licenses/>.
@
@



set FOLDER_PATH=%CD%

mkdir %ProgramFiles%\Visualsorting\

copy  %FOLDER_PATH%\VisualSortingIcon.ico %ProgramFiles%\Visualsorting\VisualSortingIcon.ico
copy  %FOLDER_PATH%\VisualSorting.jar %ProgramFiles%\Visualsorting\VisualSorting.jar
copy  %FOLDER_PATH%\splash.gif %ProgramFiles%\Visualsorting\splash.gif
copy  %FOLDER_PATH%\LICENCE.txt %ProgramFiles%\Visualsorting\LICENCE.txt


cd %ProgramFiles%\VisualSorting\
>VisualSortingRun.bat(
	@ECHO OFF
	start javaw -jar -Xms1024m -Xmx1024m -splash:%ProgramFiles%\Visualsorting\splash.gif %ProgramFiles%\Visualsorting\VisualSorting.jar -configdir:%ProgramFiles%\Visualsorting\
)

set SCRIPT="%TEMP%\%RANDOM%-%RANDOM%-%RANDOM%-%RANDOM%.vbs"
echo Set oWS = WScript.CreateObject("WScript.Shell") >> %SCRIPT% 
echo sLinkFile = "%USERPROFILE%\Desktop\VisualSorting.lnk" >> %SCRIPT% 
echo Set oLink = oWS.CreateShortcut(sLinkFile) >> %SCRIPT%
echo oLink.TargetPath = "%ProgramFiles%\VisualSorting\VisualSortingRun.bat" >> %SCRIPT%  
echo oLink.IconLocation = "%ProgramFiles%\VisualSorting\VisualSortingIcon.ico" >> %SCRIPT%  
echo oLink.WorkingDirectory = "%ProgramFiles%\VisualSorting" >> %SCRIPT% 
echo oLink.Save >> %SCRIPT% & cscript %SCRIPT% & del %SCRIPT%

