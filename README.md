# Exoplayer
ExoPlayer

So the framework is pretty simple has it only saves and sends the data to the application, if it were a product the developer developing the application needed these bit 
of documentation to understand how to use it.

The framework consists of a UtilityClass, with the documentation of ExoPlayer the developer should implement the correct listeners to every function of the UtilityClass.


There's:

<b>void handleVideoIsPlayerChanged(boolean isPlaying, long currentTimeMillis)</b> function that when is called adds to the counts of 'resumedTimes' or 'pausedTimes' depending 
on the variables passed through, and also calculates and adds to a totalTimePaused count the time that as passed between each pause.*

<b>void handleVideoRestarted</b> that should be called when the video has restarted, adding up the amount of times these action is done.*

<b>boolean checkIfVideoEnded(int state)</b> this function should be called on a videoStateChanged, receiving it's value and returning true or false depending on the state received.

<b>void resetData(Boolean resetData)</b> this function receives a boolean that determines if essencial data should be saved or not, but always resetting the totalPausedTime, 
current timePaused and pausedTimeMillis.

Then there's 5 String return functions that are already formatted to the application layout to return the correct String to display on screen. All these functions are grouped
on a single function in the MainActivity to retrieve the current data saved.

*note that everytime a value changes the developer should fetch for a data update.

>
>
>
Personal Opinion and Feedback:
I really enjoyed working on these project, it got me searching for answers and good implementation methods, I was a bit short of time because of personal stuff but still had the time 
to make a 'functional' project.

I had some doubts about the framework part since I really haven't done anything like these before and left with the idea that maybe the framework should be more responsible
about the data management and when it is called on its own.

About the architecture of the project, I trying implementing a rxJava/rxAndroid/Dagger clean archiquecture but at the end found myself spending a bit too much time trying to 
solve every last part of it, so removed the necessary to get the application running and implemented an uglier and faster Api call method. Still left a bit of the code 
I didn't use left, because I had already commited it but the last part of the code where I struggled the most I ended up just deletting it.
