/**
 * =====================================================================
 * Programming Project for NCAA Level 3, Standard 91906
 * ---------------------------------------------------------------------
 * Project Name:   Freaky Ahh  House
 * Project Author: Ben Martin
 * GitHub Repo:    https://github.com/waimea-Ben/level-3-programming-assessment
 * ---------------------------------------------------------------------
 * Notes:
 * PROJECT NOTES HERE
 * =====================================================================
 */


// import all required functions and decor
import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*


/**
 * Launch the application
 */
fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    val app = App()         // Create the app model
    roomInit(app)              // Creat and link rooms together
    MainWindow(app)         // Create and show the UI, using the app model

}


/**
 * The application class (model)
 * This is the place where any application data should be
 * stored, plus any application logic functions
 */
class App{
    // Constants defining any key values
    val key1 = "Lost Book"
    val key2 = "Gold Totem"
    val key3 = "The Key"
    val item4 = "Compass"
    val winKey = "The Eldritch Toaster"

    val maxMoves = 30

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
        if (clicks > maxMoves) clicks = maxMoves
    }


/*
 All movement functions (N,S,E,W)
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
            key1 -> {
                hasKey1 = true
                JOptionPane.showMessageDialog(
                    null,
                    "<html><p style=text-align: center;>You found a Book in the grasp of a skeleton. A note reads: <br> " +
                            "<i>A secret passage lies ahead, put this next to the book about the dead <i></p>",
                    "Found: $key1",
                    JOptionPane.INFORMATION_MESSAGE)
            }
            key2 -> {
                hasKey2 = true
                JOptionPane.showMessageDialog(
                    null,
                    "<html><p style=text-align: center;>A Golden totem sits in a small leather pouch in the corner. an ominous shriek echos as you pick it up</p>",
                    "Found: $key2",
                    JOptionPane.INFORMATION_MESSAGE)
            }
            key3 ->{
                hasKey3 = true
                JOptionPane.showMessageDialog(
                    null,
                    "<html><p style=text-align: center;>A blade of light pierces through a hole in the ceiling, illuminating a glistening golden key in the hands of a towering statue. " +
                            "<br><i>The Key to Life</i> <br> is engraved along the head.</p>",
                    "Found: $key3",
                    JOptionPane.INFORMATION_MESSAGE)

            }
            item4 ->{
                hasCompass = true
                JOptionPane.showMessageDialog(
                    null,
                    "<html><p style='text-align: center;'>A hefty compass rests by the door, its needle spinning wildly before settling in one direction.</p>",
                    "Found: $item4",
                    JOptionPane.INFORMATION_MESSAGE
                )

            }
            winKey ->{
                hasWinKey = true
                JOptionPane.showMessageDialog(
                    null,
                    "<html><p style='text-align: center;'>Within the growths of the pristine garden greens, A relic lies within. An ancient toaster that whispers in tongues. Burnt bread holds the secrets of the house.</p>",
                    "Found: $winKey",
                    JOptionPane.INFORMATION_MESSAGE
                )
            }
        }
        // removes iterm from room to prevent double searching
        currentRoom?.item = null
    }

}


/*
 room class with no directions by default
 */
class Room(val name: String, val desc: String, val direction: String){
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
    val entrance = Room("Entrance Hall", "A grand entrance with towering doors and flickering chandeliers.", "\uD83E\uDC56")
    val woodMan = Room("Woodland Mansion", "You find yourself in a dark and spooky building; bats linger around.","\uD83E\uDC56")
    val forest = Room("Forest", "An empty forest with not much around.","\uD83E\uDC56")
    val cave = Room("Cave", "Damp and cold, the echoes of dripping water fill the cavern.","\uD83E\uDC56")
    val riverbank = Room("Riverbank", "A gentle river flows by, reflecting the light of the moon.","\uD83E\uDC52")
    val bridge = Room("Old Bridge", "A rickety wooden bridge sways over a deep chasm.","\uD83E\uDC52")
    val tower = Room("Watchtower", "A tall, crumbling tower with a view of the entire landscape.","\uD83E\uDC55")
    val village = Room("Abandoned Village", "Houses stand in disrepair, long since left behind.","\uD83E\uDC54")
    val library = Room("Ancient Library", "Dusty bookshelves line the walls, filled with forgotten knowledge.","\uD83E\uDC53")
    val dungeon = Room("Dark Dungeon", "Chains hang from the walls, and the air smells of damp stone.","\uD83E\uDC54")
    val ruins = Room("Ruined Temple", "Overgrown with vines, this place holds the whispers of the past.","\uD83E\uDC51")
    val garden = Room("Hidden Garden", "A beautiful, untouched oasis filled with vibrant flowers.","·")
    val market = Room("Deserted Market", "Stalls remain, but no merchants can be found.","\uD83E\uDC54")
    val lighthouse = Room("Old Lighthouse", "A spiraling staircase leads up to a broken beacon.","\uD83E\uDC50")
    val catacombs = Room("Catacombs", "Narrow tunnels wind through the earth, filled with the bones of the past.","\uD83E\uDC50")

// instantiate connections between rooms
    entrance.locNorth = woodMan
    entrance.item = app.item4

    woodMan.locSouth = entrance
    woodMan.locWest = forest
    woodMan.locEast = library

    forest.locEast = woodMan
    forest.locSouth = cave

    cave.locNorth = forest
    cave.locEast = riverbank
    cave.item = app.key1 // adding key1 to room

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
    dungeon.item = app.key2

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
    catacombs.item = app.key3

    app.currentRoom = entrance // starting room





}

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







    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI
        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible
        updateView()                    // Initialise the UI
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
        locationDescription.bounds = Rectangle(180, 80, 200, 200)
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



    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    private fun updateView() {

        this.requestFocus()

        if (app.clicks >= app.maxMoves) {
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
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE)
            dispose()

        }
        else {
            clicksLabel.text = (app.maxMoves-app.clicks).toString()
        }

        val currentRoom = app.currentRoom


        // sets texts to respective locations when changed
        locationLabel.text = currentRoom?.name ?: "Unknown"
        locationDescription.text = "<html>" + (currentRoom?.desc ?: "No desc")


        // Shows a padlock icon on the direction arrow to indicate locked
        // and an unlocked when the user finds the key for that route
        upButton.text = when{
            currentRoom?.name == "Ruined Temple" && !app.hasKey3 -> ("\uD83D\uDD12")
            currentRoom?.name == "Dark Dungeon" && !app.hasKey1 -> ("\uD83D\uDD12")
            currentRoom?.name == "Ruined Temple" && app.hasKey3 -> ("\uD83D\uDD13")
            currentRoom?.name == "Dark Dungeon" && app.hasKey1 -> ("\uD83D\uDD13")
            else -> "N"
        }

        downButton.text = when{
            currentRoom?.name == "Ancient Library" && !app.hasKey1 -> ("\uD83D\uDD12")
            currentRoom?.name == "Ancient Library" && app.hasKey1 -> ("\uD83D\uDD13")
            else -> "S"
        }

        rightButton.text = when{
            currentRoom?.name == "Dark Dungeon" && !app.hasKey2 -> "\uD83D\uDD12"
            currentRoom?.name == "Dark Dungeon" && app.hasKey2 -> "\uD83D\uDD13"
            else -> "E"
        }

        leftButton.text = when{
            currentRoom?.name == "Abandoned Village" && !app.hasKey2 -> ("\uD83D\uDD12")
            currentRoom?.name == "Abandoned Village" && app.hasKey2 -> ("\uD83D\uDD13")
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
        inv1.text = if(app.hasKey1) app.key1 else "Empty"
        inv2.text = if(app.hasKey2) app.key2 else "Empty"
        inv3.text = if(app.hasKey3) app.key3 else "Empty"

        //shows the direction arrow for each room when moved, if has compass item
        if(app.hasCompass) compass.text = currentRoom?.direction

    }

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
        }
        updateView() // run update view
    }


// handles key presses, then updates ui
    override fun keyPressed(e: KeyEvent?) {
        when (e?.keyCode) {
            KeyEvent.VK_UP -> app.up()
            KeyEvent.VK_DOWN -> app.down()
            KeyEvent.VK_LEFT -> app.left()
            KeyEvent.VK_RIGHT -> app.right()

            KeyEvent.VK_ENTER -> app.search()
            KeyEvent.VK_S -> app.search()

            KeyEvent.VK_H -> examplePopUp.isVisible = true
            KeyEvent.VK_SLASH -> examplePopUp.isVisible = true

        }
        updateView() // run update view
    }

    // redundant functions.
    override fun keyReleased(e: KeyEvent?) {

    }
    override fun keyTyped(e: KeyEvent?) {

    }

}


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
            "<html><p><strong>Welcome to Freaky Ahh House!</strong><br><br>Explore a spooky mansion and collect keys to unlock new areas. " +
                    "Move through rooms using the direction buttons or arrow keys, and keep an eye on on the search button (S) for important items needed to progress.<br><br>" +
                    "Your goal is to find all the keys and uncover the mansion's secret garden before being removed from existence by a miserable plague released after you run out of steps. " +
                    "Some paths are locked, so you’ll need to gather items to unlock them.<br><br>Good luck, and enjoy the adventure!<br><br>H or ? to bring this menu back up.</p>"
        )
        message.bounds = Rectangle(25, 25, 475, 325)
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
    override fun keyPressed(e: KeyEvent?) {
        if (e?.keyCode == KeyEvent.VK_ESCAPE) {
            dispose()
        }

    }
// redundant function
    override fun keyReleased(e: KeyEvent?) {}
    override fun keyTyped(e: KeyEvent?) {}

    }
