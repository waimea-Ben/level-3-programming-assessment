/**
 * =====================================================================
 * Programming Project for NCAA Level 3, Standard 91906
 * ---------------------------------------------------------------------
 * Project Name:   Freaky Ahh  House
 * Project Author: Ben Martin
 * GitHub Repo:    https://github.com/waimea-Ben/level-3-programming-assessment
 * =====================================================================
 */


// import all required functions and decor
import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*
import kotlin.reflect.full.memberProperties
import javax.sound.sampled.AudioSystem




/**
 * Launch the application
 */
fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    val app = App()         // Create the app model
    roomInit(app)              // Creat and link rooms together
    MainWindow(app)         // Create and show the UI, using the app model

}
/************************************************************************************************************************/

/**
 * The application class (model)
 * This is the place where any application data should be
 * stored, plus any application logic functions
 */
class App{
    // Constants defining any key values
    val KEY1 = "Lost Book"
    val KEY2 = "Gold Totem"
    val KEY3 = "The Key"
    val COMPASS = "Compass"
    val winKey = "The Eldritch Toaster"


    val MAX_MOVES = 30

    // Data fields
    var clicks = 0
    var currentRoom: Room? = null

    var hasCompass = false
    var hasKey1 = false
    var hasKey2 = false
    var hasKey3 = false
    private var hasWinKey = false


    // Application logic functions
    private fun updateClickCount() {
        clicks++
        if (clicks > MAX_MOVES) clicks = MAX_MOVES
    }


/*
 All movement functions (N,S,E,W),
 returns before moving onto next if to ensure code is not run twice
 */
    fun up(){
        if (currentRoom?.locNorth != null) {
            if (currentRoom!!.name == "Ruined Temple" && !hasKey3){  //shows dialog explaining locked path to user hinting to item
                JOptionPane.showMessageDialog(
                    null,
                    "The path to the garden is locked, you must have the gold Key to continue",
                    "Blocked Path",
                    JOptionPane.QUESTION_MESSAGE)
                return
            }
            if (currentRoom!!.name == "Ruined Temple" && hasKey3) {  //shows dialogue about the door unlocking
                JOptionPane.showMessageDialog(
                    null,
                    "<html><p style=text-align: center;>The key slides into the lock with a satisfying click.<br>As you turn it the ground seems to shake around you as the giant archways creaks open as your blinded by the beams of light piecing through the opening</p>",
                    "Unlocked Path",
                    JOptionPane.QUESTION_MESSAGE
                )
                clicks = -550

                JOptionPane.showMessageDialog(
                    null,
                    "<html><p style=text-align: center;>The key slides into the lock with a satisfying click.<br>As you turn it the ground seems to shake around you as the giant archways creaks open as your blinded by the beams of light piecing through the opening</p>",
                    "Blocked Path",
                    JOptionPane.QUESTION_MESSAGE
                )
            }

            if (currentRoom!!.name == "Dark Dungeon" && !hasKey1){
                // show dialogue about item being needed to unlock the path.
                JOptionPane.showMessageDialog(
                    null,
                    "A path leads up but a door blocks the way, it seems an item is needed",
                    "Blocked Path",
                    JOptionPane.QUESTION_MESSAGE)
                return
            }
            currentRoom = currentRoom!!.locNorth
            updateClickCount()
        }
    }



    fun down(){
        if (currentRoom?.locSouth != null) {
            if (currentRoom!!.name == "Ancient Library" && !hasKey1){
                JOptionPane.showMessageDialog(
                    null,
                    "A path seems near, but you dont quite know how to access it",
                    "Blocked Path",
                    JOptionPane.QUESTION_MESSAGE)
                return
            }
            if (currentRoom!!.name == "Abandoned Village" && !hasKey2){
                JOptionPane.showMessageDialog(
                    null,
                    "One of the house has a basement passage, but you dont know how to get through",
                    "Blocked Path",
                    JOptionPane.QUESTION_MESSAGE)
                return
            }
            currentRoom = currentRoom!!.locSouth
            updateClickCount()
        }
    }

    fun left(){
        if (currentRoom?.locWest != null) {
            currentRoom = currentRoom!!.locWest
            updateClickCount()
        }
    }

    fun right(){
        if (currentRoom?.locEast != null) {
            if (currentRoom!!.name == "Dark Dungeon" && !hasKey2){
                JOptionPane.showMessageDialog(
                    null,
                    "A podium lays in the middle of the room, it seems an item is needed",
                    "Blocked Path",
                    JOptionPane.QUESTION_MESSAGE)
                return
            }
            currentRoom = currentRoom!!.locEast
            updateClickCount()
        }
    }

    // search function
    fun search(){
        when(currentRoom?.item){ // shows dialogue for each item and sets its has state to true
            KEY1 -> {
                hasKey1 = true
                JOptionPane.showMessageDialog(
                    null,
                    "<html><p style=text-align: center;>You found a Book in the grasp of a skeleton. A note reads: <br> " +
                            "<i>A secret passage lies ahead, put this next to the book about the dead <i></p>",
                    "Found: $KEY1",
                    JOptionPane.INFORMATION_MESSAGE)
            }
            KEY2 -> {
                hasKey2 = true
                JOptionPane.showMessageDialog(
                    null,
                    "<html><p style=text-align: center;>A Golden totem sits in a small leather pouch in the corner. an ominous shriek echos as you pick it up</p>",
                    "Found: $KEY2",
                    JOptionPane.INFORMATION_MESSAGE)
            }
            KEY3 ->{
                hasKey3 = true
                JOptionPane.showMessageDialog(
                    null,
                    "<html><p style=text-align: center;>A blade of light pierces through a hole in the ceiling, illuminating a glistening golden key in the hands of a towering statue. " +
                            "<br><i>The Key to Life</i> <br> is engraved along the head.</p>",
                    "Found: $KEY3",
                    JOptionPane.INFORMATION_MESSAGE)

            }
            COMPASS ->{
                hasCompass = true
                JOptionPane.showMessageDialog(
                    null,
                    "<html><p style='text-align: center;'>A hefty compass rests by the door, its needle spinning wildly before settling in one direction.</p>",
                    "Found: $COMPASS",
                    JOptionPane.INFORMATION_MESSAGE
                )

            }
            winKey ->{
                hasWinKey = true
                JOptionPane.showMessageDialog(
                    null,
                    "<html><p style='text-align: center;'>Within the growths of the pristine garden greens, A relic lies within. An ancient toaster that whispers in tongues. Burnt bread holds the secrets of the house. <br> You have survived the plague and live happily ever after yay</p>",
                    "Found: $winKey",
                    JOptionPane.INFORMATION_MESSAGE
                )
                System.exit(0)

            }
        }
        // removes iterm from room to prevent double searching
        currentRoom?.item = null
    }


    val compassDirections = listOf("\uD83E\uDC51","\uD83E\uDC55","\uD83E\uDC52","\uD83E\uDC56","\uD83E\uDC53","\uD83E\uDC57","\uD83E\uDC50","\uD83E\uDC54")

    fun compassSpin(){
        if(currentRoom?.name == "Hidden Garden") {
            currentRoom?.direction = compassDirections.random()
        }
    }

}

/***************************************************************************************************************************/

/*
 room class with no directions by default
 */
class Room(val name: String, val desc: String, var direction: String){
    var locNorth: Room? = null
    var locEast: Room? = null
    var locSouth: Room? = null
    var locWest: Room? = null
    var item: String? = null

}

/*
 populate map with locations
 */
fun roomInit(app: App){
    //Direction constants
    val N = "\uD83E\uDC51"
    val NE = "\uD83E\uDC55"
    val E = "\uD83E\uDC52"
    val SE = "\uD83E\uDC56"
    val S = "\uD83E\uDC53"
//  val SW = "\uD83E\uDC57" not used as no rooms to north east of gardens
    val W = "\uD83E\uDC50"
    val NW = "\uD83E\uDC54"

    val entrance = Room("Entrance Hall", "<html><p style=text-align: center;>A grand entrance with towering doors and flickering chandeliers.", SE)
    val woodMan = Room("Woodland Mansion", "<html><p style=text-align: center;>You find yourself in a dark and spooky building; bats linger around.",SE)
    val forest = Room("Forest", "<html><p style=text-align: center;>An empty forest with not much around.",SE)
    val cave = Room("Cave", "<html><p style=text-align: center;>Damp and cold, the echoes of dripping water fill the cavern.",SE)
    val riverbank = Room("Riverbank", "<html><p style=text-align: center;>A gentle river flows by, reflecting the light of the moon.",E)
    val bridge = Room("Old Bridge", "<html><p style=text-align: center;>A rickety wooden bridge sways over a deep chasm.",E)
    val tower = Room("Watchtower", "<html><p style=text-align: center;>A tall, crumbling tower with a view of the entire landscape.",NE)
    val village = Room("Abandoned Village", "<html><p style=text-align: center;>Houses stand in disrepair, long since left behind.",NW)
    val library = Room("Ancient Library", "<html><p style=text-align: center;>Dusty bookshelves line the walls, filled with forgotten knowledge.",S)
    val dungeon = Room("Dark Dungeon", "<html><p style=text-align: center;>Chains hang from the walls, and the air smells of damp stone.",NW)
    val ruins = Room("Ruined Temple", "<html><p style=text-align: center;>Overgrown with vines, this place holds the whispers of the past.",N)
    val garden = Room("Hidden Garden", "<html><p style=text-align: center;>A beautiful, untouched oasis filled with vibrant flowers.","·")
    val market = Room("Deserted Market", "<html><p style=text-align: center;>Stalls remain, but no merchants can be found.",NW)
    val lighthouse = Room("Old Lighthouse", "<html><p style=text-align: center;>A spiraling staircase leads up to a broken beacon.",W)
    val catacombs = Room("Catacombs", "<html><p style=text-align: center;>Narrow tunnels wind through the earth, filled with the bones of the past.",W)

// instantiate connections between rooms
    entrance.locNorth = woodMan
    entrance.item = app.COMPASS

    woodMan.locSouth = entrance
    woodMan.locWest = forest
    woodMan.locEast = library

    forest.locEast = woodMan
    forest.locSouth = cave

    cave.locNorth = forest
    cave.locEast = riverbank
    cave.item = app.KEY1 // adding key1 to room

    riverbank.locWest = cave
    riverbank.locSouth = bridge

    bridge.locNorth = riverbank
    bridge.locEast = tower

    tower.locWest = bridge
    tower.locEast = ruins

    village.locEast = market
    village.locWest = dungeon

    library.locWest = woodMan
    library.locSouth = dungeon

    dungeon.locNorth = library
    dungeon.locEast = village
    dungeon.locWest = ruins
    dungeon.item = app.KEY2

    ruins.locWest = tower
    ruins.locEast = dungeon
    ruins.locNorth = garden

    garden.locSouth = ruins
    garden.item = app.winKey

    market.locWest = village
    market.locNorth = lighthouse

    lighthouse.locSouth = market
    lighthouse.locWest = catacombs

    catacombs.locEast = lighthouse
    catacombs.item = app.KEY3

    app.currentRoom = entrance // starting room





}
/***************************************************************************************************************************/

/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passwd as an argument
 */
class MainWindow(private val app: App) : JFrame(), ActionListener, KeyListener {

    // Fields to hold the UI elements
    private lateinit var clicksLabel: JLabel

    private lateinit var upButton: JButton
    private lateinit var downButton: JButton
    private lateinit var leftButton: JButton
    private lateinit var rightButton: JButton

    private lateinit var locationLabel: JLabel
    private lateinit var locationDescription: JLabel

    private lateinit var inv1: JLabel
    private lateinit var inv2: JLabel
    private lateinit var inv3: JLabel

    private lateinit var searchButton: JButton
    private lateinit var examplePopUp: PopUpDialog
    private lateinit var compass: JLabel

    private lateinit var compassSpinClock: Timer

    //Costants
    val LOCKED = "\uD83D\uDD12"
    val UNLOCKED = "\uD83D\uDD13"


    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI
        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible
        updateView()                    // Initialise the UI

        compassSpinClock.start()
    }

    /**
     * Configure the main window
     */
    private fun configureWindow() {
        title = "Key to Life"
        contentPane.preferredSize = Dimension(500, 350)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null


        // Add Help button to top bar
        val helpButton = JButton("?").apply {
            preferredSize = Dimension(45, 30)
            maximumSize = preferredSize
            minimumSize = preferredSize
            isFocusPainted = false
            isBorderPainted = false
            isContentAreaFilled = true // FlatLaf styling applies only if true
            background = UIManager.getColor("control")
            toolTipText = "Help"
            border = BorderFactory.createEmptyBorder()

            // Hover effect
            addMouseListener(object : MouseAdapter() {
                override fun mouseEntered(e: MouseEvent?) {
                    background = Color(85, 88, 92) // Light gray hover effect
                }

                override fun mouseExited(e: MouseEvent?) {
                    background = UIManager.getColor("control") // Restore default
                }
            })

            addActionListener {
                examplePopUp.isVisible = true
            }
        }


        // Get the window's top-right control buttons
        val menuBar = JMenuBar().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            add(Box.createHorizontalGlue()) // Push the button to the right
            add(helpButton)
        }
        jMenuBar = menuBar // Set as title bar menu


        // arrange elements
        pack()
    }



    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {

        // show help on start
        examplePopUp = PopUpDialog()
        examplePopUp.isVisible = true

        // listener for direction and function key presses
        this.addKeyListener(this)

        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 36)

        compassSpinClock = Timer(200, this)

        compass = JLabel("")
        compass.font = baseFont
        compass.bounds = Rectangle(50, 0, 100, 100)
        add(compass)

        // amount of moves left based on direction changes
        clicksLabel = JLabel("50")
        clicksLabel.horizontalAlignment = SwingConstants.CENTER
        clicksLabel.bounds = Rectangle(50, 288, 100, 50)
        clicksLabel.font = baseFont
        add(clicksLabel)


        locationLabel = JLabel("location")
        locationLabel.horizontalAlignment = SwingConstants.CENTER
        locationLabel.bounds = Rectangle(50, 20, 400, 54)
        locationLabel.font = baseFont
        add(locationLabel)

        locationDescription = JLabel("desc")
        locationDescription.bounds = Rectangle(150, 80, 200, 200)
        locationDescription.setVerticalAlignment(SwingConstants.TOP)
        locationDescription.font = Font(Font.SANS_SERIF, Font.PLAIN, 16)
        add(locationDescription)

        // direction arrows
        upButton = JButton("N")
        upButton.bounds = Rectangle(225, 233, 50, 50)
        upButton.font = baseFont
        upButton.addActionListener(this)
        upButton.isFocusable = false
        upButton.toolTipText = "North"
        add(upButton)

        downButton = JButton("S")
        downButton.bounds = Rectangle(225, 288, 50, 50)
        downButton.font = baseFont
        downButton.addActionListener(this)
        downButton.isFocusable = false
        downButton.toolTipText = "South"
        add(downButton)

        leftButton = JButton("W")
        leftButton.bounds = Rectangle(170, 288, 50, 50)
        leftButton.font = baseFont
        leftButton.addActionListener(this)
        leftButton.isFocusable = false
        leftButton.toolTipText = "West"
        add(leftButton)

        rightButton = JButton("E")
        rightButton.bounds = Rectangle(280, 288, 50, 50)
        rightButton.font = baseFont
        rightButton.addActionListener(this)
        rightButton.isFocusable = false
        rightButton.toolTipText = "East"
        add(rightButton)

        // show inventory items
        inv1 = JLabel("Empty")
        inv1.bounds = Rectangle(18, 100, 150, 50)
        inv1.font = Font(Font.SANS_SERIF, Font.PLAIN, 28)
        add(inv1)

        inv2 = JLabel("Empty")
        inv2.bounds = Rectangle(18, 150, 150, 50)
        inv2.font = Font(Font.SANS_SERIF, Font.PLAIN, 28)
        add(inv2)

        inv3 = JLabel("Empty")
        inv3.bounds = Rectangle(18, 200, 150, 50)
        inv3.font = Font(Font.SANS_SERIF, Font.PLAIN, 28)
        add(inv3)

        // search button
        searchButton = JButton("\uD83D\uDD0E")
        searchButton.bounds = Rectangle(375, 200, 75, 75)
        searchButton.font = baseFont
        searchButton.addActionListener(this)
        searchButton.isFocusable = false
        searchButton.toolTipText = "Search"
        add(searchButton)



    }


    /***************************************************************************************************************************/
    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    private fun updateView() {

        this.requestFocus()

        if (app.clicks >= app.MAX_MOVES) {
            // Disable movement buttons after death
            upButton.isEnabled = false
            downButton.isEnabled = false
            leftButton.isEnabled = false
            rightButton.isEnabled = false
            searchButton.isEnabled = false
            clicksLabel.text = "0"
            JOptionPane.showMessageDialog(
                null,
                "<html><p style=text-align: center;>The ghastly plague is released, removing you from the timeline of life <br> \n" +
                        "<i>for you, the game is over... <i></p>",
                "Intentional game design",
                JOptionPane.INFORMATION_MESSAGE)
            dispose()

        }
        else {
            clicksLabel.text = (app.MAX_MOVES-app.clicks).toString()
        }

        val currentRoom = app.currentRoom


        // sets texts to respective locations when changed
        locationLabel.text = currentRoom?.name ?: "Unknown"
        locationDescription.text = "<html>" + (currentRoom?.desc ?: "No desc")


        // Shows a padlock icon on the direction arrow to indicate locked
        // and an unlocked when the user finds the key for that route
        upButton.text = when{
            currentRoom?.name == "Ruined Temple" && !app.hasKey3 -> LOCKED
            currentRoom?.name == "Dark Dungeon" && !app.hasKey1 -> LOCKED
            currentRoom?.name == "Ruined Temple" && app.hasKey3 -> UNLOCKED
            currentRoom?.name == "Dark Dungeon" && app.hasKey1 -> UNLOCKED
            else -> "N"
        }

        downButton.text = when{
            currentRoom?.name == "Ancient Library" && !app.hasKey1 -> LOCKED
            currentRoom?.name == "Ancient Library" && app.hasKey1 -> UNLOCKED
            else -> "S"
        }

        rightButton.text = when{
            currentRoom?.name == "Dark Dungeon" && !app.hasKey2 -> LOCKED
            currentRoom?.name == "Dark Dungeon" && app.hasKey2 -> UNLOCKED
            else -> "E"
        }

        leftButton.text = when{
            currentRoom?.name == "Abandoned Village" && !app.hasKey2 -> LOCKED
            currentRoom?.name == "Abandoned Village" && app.hasKey2 -> UNLOCKED
            else -> "W"
        }


        //disables buttons when no direction available
        upButton.isEnabled = currentRoom?.locNorth != null
        downButton.isEnabled = currentRoom?.locSouth != null
        leftButton.isEnabled = currentRoom?.locWest != null
        rightButton.isEnabled = currentRoom?.locEast != null

        // no item no search
        searchButton.isEnabled = currentRoom?.item != null

        // when item found update inventory slot
        inv1.text = if(app.hasKey1) app.KEY1 else "Empty"
        inv2.text = if(app.hasKey2) app.KEY2 else "Empty"
        inv3.text = if(app.hasKey3) app.KEY3 else "Empty"

        //shows the direction arrow for each room when moved, if has compass item
        if(app.hasCompass) compass.text = currentRoom?.direction


    }

/***************************************************************************************************************************/

    /**
     * secret key codes
     * when sequence entered secret functions run
     * secret code 1 is left right up down b a enter
     */
    val secretCode1 = listOf(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_B, KeyEvent.VK_A, KeyEvent.VK_ENTER)
    val secretCode2 = listOf(KeyEvent.VK_P, KeyEvent.VK_L, KeyEvent.VK_A, KeyEvent.VK_Y)

    val recentKeys = mutableListOf<Int>()
    val maxLen = secretCode1.size

// when secret code one run teleports user to the room they enter
    fun secretcode1() {
        val input = JOptionPane.showInputDialog(null, "Which room to teleport to?", "Secret Code", JOptionPane.PLAIN_MESSAGE)
        if (input != null) {
            val room = findRoomByName(app.currentRoom, input.trim())
            if (room != null) {
                app.currentRoom = room
            } else {
                JOptionPane.showMessageDialog(null, "No room found with the name \"$input\".", "Error", JOptionPane.ERROR_MESSAGE)
            }
        }
    }

/* flood searches for the room as cannot use "current room = input" due to mismatch
*  uses recursive search
*  avoids revistitng rooms by kkeeping track of visited ones
*  searches all neighbouring rooms of current room, the searches all  of their neighbours etc
 */
    fun findRoomByName(current: Room?, name: String, visited: MutableSet<Room> = mutableSetOf()): Room? {
        if (current == null || current in visited) return null
        if (current.name.equals(name, ignoreCase = true)) return current
        visited.add(current)

        // Check each connected room for room
        val neighbors = listOf(current.locNorth, current.locEast, current.locSouth, current.locWest)
        for (room in neighbors) {
            val found = findRoomByName(room, name, visited)
            if (found != null) return found
        }
        return null
    }

    // play sounds when secret code two called via soundStream plugin
    fun secretCode2(){
        val soundFile = this::class.java.getResourceAsStream("sounds/metal-pipe.wav")
        val soundStream = AudioSystem.getAudioInputStream(soundFile)
        val soundClip = AudioSystem.getClip()
        soundClip.open(soundStream)
        soundClip.start()
    }

    /***************************************************************************************************************************/
    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            upButton -> app.up()
            downButton -> app.down()
            leftButton -> app.left()
            rightButton -> app.right()

            searchButton -> app.search()

            //randomly spin compass every time a tick recieved
            compassSpinClock -> app.compassSpin()
        }
        updateView() // run update view
    }


    // handles key presses, then updates ui
    override fun keyPressed(e: KeyEvent?) {

        // Adds recent key presses to lists for cheat code system
        recentKeys.add(e?.keyCode ?: return)

        //ensures recent keys does not exceed cheat code length
        if (recentKeys.size > maxLen) recentKeys.removeAt(0)

        if(recentKeys == secretCode1) secretcode1()

        //because secret code 2 is only four characters long, only read the last 4 digits of recentKeys
        if (recentKeys.takeLast(4) == secretCode2) secretCode2()


        when (e.keyCode) {
        // Direction/arrow keys
            KeyEvent.VK_UP -> app.up()
            KeyEvent.VK_DOWN -> app.down()
            KeyEvent.VK_LEFT -> app.left()
            KeyEvent.VK_RIGHT -> app.right()

        //WASD keys
            KeyEvent.VK_W -> app.up()
            KeyEvent.VK_S -> app.down()
            KeyEvent.VK_A -> app.left()
            KeyEvent.VK_D -> app.right()

        //Search keys
            KeyEvent.VK_SHIFT -> app.search()

        //Bring popup help menu back when either ? or H key pressed
            KeyEvent.VK_H -> examplePopUp.isVisible = true
            KeyEvent.VK_SLASH -> examplePopUp.isVisible = true

        }
        updateView() // run update view
    }

    //redundant functions
    override fun keyReleased(e: KeyEvent?) {}
    override fun keyTyped(e: KeyEvent?) {}
}


/***************************************************************************************************************************/
/*
Pop-up to show relevant details instructions and help
 */
class PopUpDialog: JDialog(), KeyListener {

    init {
        configureWindow()
        setLocationRelativeTo(null)
        addControls()
        addKeyListener(this)
    }

// populate popup with controls
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 16)

        val message = JLabel(
            "<html><p style=text-align: center;><strong>Welcome to Freaky Ahh House!</strong><br><br>Explore a spooky mansion and collect keys to unlock new areas. " +
                    "Move through rooms using the direction buttons or arrow keys, and keep an eye on on the search button (SHIFT) for important items needed to progress.<br><br>" +
                    "Your goal is to find all the keys and uncover the mansion's secret garden before being removed from existence by a miserable plague released after you run out of steps. " +
                    "Some paths are locked, so you’ll need to gather items to unlock them.<br><br>Good luck, and enjoy the adventure!<br><br>H or ? to bring this menu back up.</p>"
        )
        message.bounds = Rectangle(15, 25, 475, 325)
        message.verticalAlignment = SwingConstants.TOP
        message.font = baseFont
        add(message)
    }

    private fun configureWindow() {
        title = "Key To life Help"
        contentPane.preferredSize = Dimension(500, 350)
        isResizable = false
        isModal = true
        layout = null

        pack()
    }


//when escape key pressed close popup
    override fun keyPressed(e: KeyEvent?){
        if (e?.keyCode == KeyEvent.VK_ESCAPE) dispose()
    }

// redundant function
    override fun keyReleased(e: KeyEvent?) {}
    override fun keyTyped(e: KeyEvent?) {}

}

/***************************************************************************************************************************/