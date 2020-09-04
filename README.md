# Space-Shooter-Game-Development
- A space shooter game developed with Java Libgdx Framework, which uses of Architectural pattern and Design pattern for Game Programming.

## Project Vision
- Project Vision.txt

## How to build
- The computer should have JDK 8
- Download Zip or use git to clone the project to local
- If you want to build yourself, run "./gradlew desktop:dist" under the root directory of the project
- You can also run the jar directly by running "java -jar ./desktop/build/libs/desktop-1.0.jar"

## How to play
- Use Arrow Keys to move the hero
- Press left Shift to reduce the moving speed
- Press Space to shoot bullets
- Press left Ctrl to throw bomb
- Each time you earn 1000 points score, you would get one more bomb
- You can start cheat mode in setting.

# Architectural Design Goals
* We have several architectural quality attributes that we focus on which can enhance the overall software quality of our project. The goals of the design qualities which we apply in the system are concentrated at ensuring the runtime behavior, system design, and project architecture are streamlined, efficient and clean.

* The primary quality goal that our system focuses on is Flexibility, which is a quality attribute that
expresses how easy, convenient, and fast necessary changes to the systems can be conducted. Since the
design pattern (i.e. Factory Pattern) we use in our project can smoothly encapsulate the create-process of the instance and also not necessary to modify the original code when we add a new product or feature, really follow the concept of Open-Closed Principle. Flexible software architecture is essential in our project, it makes our whole system can seamless extensions and upgrades without encompassing structural changes.

* Another indispensable quality goal that our system focuses on is Modifiability, which is a quality attribute that expresses how to ease with which a software system or component can be modified to correct faults, improve performance, or adapt to a changing environment. Also, the ease with which a hardware system or component can be retained in, or restored to, a state in which it can perform its required functions. In our project, we design that the bullet manager contains the bullet factory attribute, and the bullet factory contains the shooting actions attribute. We can assign multiple shooting actions in the bullet object. This can provide wonderful modifiability in the system since it reduces the coupling of the system, we can choose our favorable shooting actions in the runtime and will not depend on the bullet object.

# Multi-Layered Architectural Pattern
* There are three component layers in the design: the Render Tier, the Presentation Tier, and the Logic Tier.
* The whole game is a flow of slices, each of which renders a game state at that moment. Each slice is different from the previous slice by some updates. Moreover, the slices are grouped into three stages: the begin stage (main menu), the play stage (play mode), and the end stage (game end). The Render Tier manages the flow between slices and between stages.
* The Presentation Tier implements the user interface, which includes interactive gadgets such as buttons and the presentation of the game itself (background, music, all characters in the game, …).
* The Logic Tier implements all logics of the system. These main logics include:
    * How the score is calculated
    * How to store and retrieve data in a proper manner
    * How users control the main character
    * How characters move, shoot, and interact with each other
    
# Design Pattern
## Factory Pattern
* In this project, we implemented a factory pattern to create enemies and bullets. Since the factory pattern provides flexibility to the code, we are not necessary to modify original code when adding a new product. On the other hand, we can also reduce the parameters passed in the constructor to create objects, just call factory to create objects.
## Command Pattern
* The bullet factory takes order with shooting action, one bullet can own multiple shooting actions, and bullet object will render those orders to the game. This pattern provides good modifiability to the code, since it reduces the coupling of the system, the shooting actions will not depend on the bullet object. We can choose to add shooting action by ourselves.
## Singleton Pattern
* Singleton pattern force program to create an object once, and other threads can neither access this object simultaneously, nor create this object again. For the accuracy and integrity of the program, this pattern prevents the class from being modified by the user at the same time, the modification at same time will cause the data not synchronized and become not accurate. Besides, singleton provides a close environment that reduces the probability of generating extra memory in the program, it will also reduce the probability of the crash of the program happening.
