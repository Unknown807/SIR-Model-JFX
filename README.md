# SIR-Model-JFX
JDK 15.0.1
# Dependencies
* javafx-fxml - 15.0.1
* javafx-controls - 15.0.1
* javafx-graphics - 15.0.1
# Description
This program is meant to model and slightly animate infectious diseases, as explained in wikipedia on how the SIR model works https://en.wikipedia.org/wiki/Compartmental_models_in_epidemiology#The_SIR_model_2
The program uses the formulas from the above article instead of running a simulation:

# How to Use

When the program starts it will have some default values and max values for the sliders.

![alt text](/imgs/img1.JPG)

Using any of the sliders will smoothly transition the series' on the graph. In this case the total time slider stretches/shrinks the graph horizontally, because its changing the upper bound of the x-axis.

![alt text](/imgs/img2.JPG)

Finally, by inputting into the bottom left fields new max values for certain sliders you can also alter the way the graph looks.

![alt text](/imgs/img3.JPG)
