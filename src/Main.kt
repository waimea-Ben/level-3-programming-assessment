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
    val maxMoves = 25

    // Data fields
    var clicks = 0
    var currentRoom: Room? = null

    var hasKey1 = false
    var hasKey2 = false
    var hasKey3 = false

    // Application logic functions
    fun updateClickCount() {
        clicks++
        if (clicks > maxMoves) clicks = maxMoves
    }

    fun die(){
        println("dead")
    }

}


// room class with no directions by default
class Room(val name: String, val desc: String){
    var locNorth: Room? = null
    var locEast: Room? = null
    var locSouth: Room? = null
    var locWest: Room? = null
    var item: String? = null

}


fun roomInit(app: App){ // populate map with locations
    val entrance = Room("Entrance Hall", "A grand entrance with towering doors and flickering chandeliers.")
    val woodMan = Room("Woodland Mansion", "You find yourself in a dark and spooky building; bats linger around.")
    val forest = Room("Forest", "An empty forest with not much around.")
    val cave = Room("Cave", "Damp and cold, the echoes of dripping water fill the cavern.")
    val riverbank = Room("Riverbank", "A gentle river flows by, reflecting the light of the moon.")
    val bridge = Room("Old Bridge", "A rickety wooden bridge sways over a deep chasm.")
    val tower = Room("Watchtower", "A tall, crumbling tower with a view of the entire landscape.")
    val village = Room("Abandoned Village", "Houses stand in disrepair, long since left behind.")
    val library = Room("Ancient Library", "Dusty bookshelves line the walls, filled with forgotten knowledge.")
    val dungeon = Room("Dark Dungeon", "Chains hang from the walls, and the air smells of damp stone.")
    val ruins = Room("Ruined Temple", "Overgrown with vines, this place holds the whispers of the past.")
    val garden = Room("Hidden Garden", "A beautiful, untouched oasis filled with vibrant flowers.")
    val market = Room("Deserted Market", "Stalls remain, but no merchants can be found.")
    val lighthouse = Room("Old Lighthouse", "A spiraling staircase leads up to a broken beacon.")
    val catacombs = Room("Catacombs", "Narrow tunnels wind through the earth, filled with the bones of the past.")

// instantiate connections between rooms
    entrance.locNorth = woodMan

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
class MainWindow(private val app: App) : JFrame(), ActionListener {

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


        //*********************************************************************************************************
        // borrowed code. need to replace

        val helpButton = JButton("?").apply {
            preferredSize = Dimension(45, 25) // Match title bar button size
            maximumSize = preferredSize
            minimumSize = preferredSize
            isFocusPainted = false
            isBorderPainted = false
            isContentAreaFilled = false
            background = UIManager.getColor("control") // Default background

            // Hover effect
            addMouseListener(object : MouseAdapter() {
                override fun mouseEntered(e: MouseEvent?) {
                    background = Color(85, 88, 92) // Light gray hover effect
                    isContentAreaFilled = true
                }

                override fun mouseExited(e: MouseEvent?) {
                    background = UIManager.getColor("control") // Restore default
                    isContentAreaFilled = false
                }
            })

            addActionListener {
                JOptionPane.showMessageDialog(this, "This is a help dialog.", "Help", JOptionPane.INFORMATION_MESSAGE)
            }
        }

        // Get the window's top-right control buttons
        val menuBar = JMenuBar().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            add(Box.createHorizontalGlue()) // Push the button to the right
            add(helpButton)
        }
        jMenuBar = menuBar // Set as title bar menu

        // Remove window corner radius (sharp corners)
        rootPane.border = BorderFactory.createEmptyBorder() // Ensure no padding or rounded edges

        //*********************************************************************************************************


        pack()
    }



    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 36)

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

        upButton = JButton("N")
        upButton.bounds = Rectangle(225, 233, 50, 50)
        upButton.font = baseFont
        upButton.addActionListener(this)
        add(upButton)

        downButton = JButton("S")
        downButton.bounds = Rectangle(225, 288, 50, 50)
        downButton.font = baseFont
        downButton.addActionListener(this)
        add(downButton)

        leftButton = JButton("W")
        leftButton.bounds = Rectangle(170, 288, 50, 50)
        leftButton.font = baseFont
        leftButton.addActionListener(this)
        add(leftButton)

        rightButton = JButton("E")
        rightButton.bounds = Rectangle(280, 288, 50, 50)
        rightButton.font = baseFont
        rightButton.addActionListener(this)
        add(rightButton)

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

        searchButton = JButton("\uD83D\uDD0E")
        searchButton.bounds = Rectangle(375, 200, 75, 75)
        searchButton.font = baseFont
        searchButton.addActionListener(this)
        add(searchButton)



    }


    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    private fun updateView() {
        if (app.clicks == app.maxMoves) {
            app.die()
            clicksLabel.text = "0"

        }
        else {
            clicksLabel.text = (app.maxMoves-app.clicks).toString()
        }

        val currentRoom = app.currentRoom

        locationLabel.text = currentRoom?.name ?: "Unknown"
        locationDescription.text = "<html>" + (currentRoom?.desc ?: "No desc")


        // Shows a padlock icon on the direction arrow to indicate locked route
        upButton.text = when{
            currentRoom?.name == "Ruined Temple" && !app.hasKey3 -> ("\uD83D\uDD12")
            currentRoom?.name == "Dark Dungeon" && !app.hasKey1 -> ("\uD83D\uDD12")
            else -> "N"
        }

        downButton.text = when{
            currentRoom?.name == "Ancient Library" && !app.hasKey1 -> ("\uD83D\uDD12")
            currentRoom?.name == "Abandoned Villager" && !app.hasKey2 -> ("\uD83D\uDD12")
            else -> "S"
        }

        rightButton.text = when{
            currentRoom?.name == "Dark Dungeon" && !app.hasKey2 -> "\uD83D\uDD12"
            else -> "E"
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


    }

    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        val currentRoom = app.currentRoom
        when (e?.source) {

            upButton -> {
                if (currentRoom?.locNorth != null) {
                    if (currentRoom.name == "Ruined Temple" && !app.hasKey3){  //shows dialog explaining locked path to user hinting to item
                        JOptionPane.showMessageDialog(this, "The path to the garden is locked, you must have the gold Key to continue","Blocked Path",JOptionPane.QUESTION_MESSAGE)
                        return
                    }
                    if (currentRoom.name == "Dark Dungeon" && !app.hasKey1){
                        JOptionPane.showMessageDialog(this, "A path leads up but a door blocks the way, it seems an item is needed","Blocked Path",JOptionPane.QUESTION_MESSAGE)
                        return
                    }
                    app.currentRoom = currentRoom.locNorth
                    app.updateClickCount()
                }

            }

            downButton -> {
                if (currentRoom?.locSouth != null) {
                    if (currentRoom.name == "Ancient Library" && !app.hasKey1){
                        JOptionPane.showMessageDialog(this, "A path seems near, but you dont quite know how to access it","Blocked Path",JOptionPane.QUESTION_MESSAGE)
                        return
                    }
                    if (currentRoom.name == "Abandoned Village" && !app.hasKey2){
                        JOptionPane.showMessageDialog(this, "One of the house has a basement passage, but you dont know how to get through","Blocked Path",JOptionPane.QUESTION_MESSAGE)
                        return
                    }
                    app.currentRoom = currentRoom.locSouth
                    app.updateClickCount()
                }
            }

            leftButton -> {
                if (currentRoom?.locWest != null) {
                    app.currentRoom = currentRoom.locWest
                    app.updateClickCount()
                }
            }

            rightButton -> {
                if (currentRoom?.locEast != null) {
                    if (currentRoom.name == "Dark Dungeon" && !app.hasKey2){
                        JOptionPane.showMessageDialog(this, "A podium lays in the middle of the room, it seems an item is needed","Blocked Path",JOptionPane.QUESTION_MESSAGE)
                        return
                    }
                    app.currentRoom = currentRoom.locEast
                    app.updateClickCount()
                }
            }


            // when search button pressed shows Dialog about respective item and then removes item from room
            searchButton -> {
                    when(currentRoom?.item){
                        app.key1 -> {
                            app.hasKey1 = true
                            JOptionPane.showMessageDialog(this, "<html><p style=text-align: center;>You found a Book in the grasp of a skeleton. A note reads: <br> <i>A secret passage lies ahead, put this next to the book about the dead <i></p>")
                        }
                        app.key2 -> {
                            app.hasKey2 = true
                            JOptionPane.showMessageDialog(this, "<html><p style=text-align: center;>A Golden totem sits in a small leather pouch in the corner. an ominous shriek echos as you pick it up</p>")
                        }
                        app.key3 ->{
                            app.hasKey3 = true
                            JOptionPane.showMessageDialog(this, "<html><p style=text-align: center;>A blade of light pierces through a hole in the ceiling, illuminating a glistening golden key in the hands of a towering statue. <br><i>The Key to Life</i> <br> is engraved along the head.</p>")

                        }
                    }
                    app.currentRoom?.item = null
            }

        }
        updateView() // run update view
    }

}

