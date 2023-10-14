*--------------------------------*
*   Hw_5_EPL222_WEB_CRAWLER      *
*    Elia Nicolaou 1012334       *
*--------------------------------*

This is the readme.txt for my program . 
My program consists of 7 classes. 
ToDo_Lists,Web_Crawler_Main,MyURL,
Monitor_URL,Monitor_URL_Imp
LinkCatcher & Link

Web_Crawler_Main -> this is my main class and the one that asked the user to add some 
information . 

INFORMATION FOR RUNNING THE PROGRAM 
-----------------------------------
The user must give : 1. the number of threads 2. the depth that we want to reach 
3.the starting link if he/she want to.

There are questions that guide the user to give the information 
FIRST
-- Please give the number of threads:
SECOND
-- Please give the number of depth you want to reach :
THIRD
--- Do you want to give the root (starting link ) : answer 1 for yes , 0 for no --- 
In order to give your own link type 1 , else give 0 

--------------------------------------------------------------------------------
My program ends when it finishes the last link of the given depth to reach. 

I've tried some examples:

"https://www.youtube.com/"
--- Please give the first URL - the first link : https://www.youtube.com/
 <depth>    <thread id>   <URL>
 < 0 >  < 0 >  < https://www.youtube.com/ > 
 < 1 >  < 1 >  < https://www.youtube.com///www.youtube.com/upload > 
 < 1 >  < 3 >  < https://www.youtube.com//feed/trending?disable_polymer=1 > 
I am thread with ID : 3and I am Waiting because I am in a different depth
I am thread with ID : 2and I am Waiting because I am in a different depth
I am thread with ID : 1and I am Waiting because I am in a different depth
--- WE REACH THE DEPTH WE WANTED :) . END OF THE PROGRAM !!! ---
 

"https://www.amazon.co.uk/"
it goes into an infinity loop as there aren't any links to get.