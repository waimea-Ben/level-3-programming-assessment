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
    val maxClicks = 50

    // Data fields
    var clicks = 0
    var currentRoom: Room? = null

    var hasKey1 = false
    var hasKey2 = false
    var hasKey3 = false

    // Application logic functions
    fun updateClickCount() {
        clicks++
        if (clicks > maxClicks) clicks = maxClicks
    }


}



class Room(val name: String, val desc: String){
    var locNorth: Room? = null
    var locEast: Room? = null
    var locSouth: Room? = null
    var locWest: Room? = null
    var item: String? = null

}


fun roomInit(app: App){
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


    entrance.locNorth = woodMan

    woodMan.locSouth = entrance
    woodMan.locWest = forest
    woodMan.locEast = library

    forest.locEast = woodMan
    forest.locSouth = cave

    cave.locNorth = forest
    cave.locEast = riverbank
    cave.item = "Key 1"

    riverbank.locWest = cave
    riverbank.locSouth = bridge

    bridge.locNorth = riverbank
    bridge.locEast = tower

    tower.locWest = bridge
    tower.locSouth = village
    tower.locEast = ruins

    village.locNorth = tower
    village.locEast = market
    village.locWest = dungeon

    library.locWest = woodMan
    library.locSouth = dungeon

    dungeon.locNorth = library
    dungeon.locEast = village
    dungeon.locWest = ruins
    dungeon.item = "Key 2"

    ruins.locWest = tower
    ruins.locEast = dungeon
    ruins.locNorth = garden

    garden.locSouth = ruins

    market.locWest = village
    market.locNorth = lighthouse

    lighthouse.locSouth = market
    lighthouse.locWest = catacombs

    catacombs.locEast = lighthouse
    catacombs.item = "Key 3"

    app.currentRoom = entrance
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
        title = "Key to life"
        contentPane.preferredSize = Dimension(500, 350)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

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
        if (app.clicks == app.maxClicks) {
            clicksLabel.text = "0"

        }
        else {
            clicksLabel.text = (50-app.clicks).toString()
        }

        val currentRoom = app.currentRoom

        locationLabel.text = currentRoom?.name ?: "Unknown"
        locationDescription.text = "<html>" + (currentRoom?.desc ?: "No desc")

        upButton.isEnabled = currentRoom?.locNorth != null
        downButton.isEnabled = currentRoom?.locSouth != null
        leftButton.isEnabled = currentRoom?.locWest != null
        rightButton.isEnabled = currentRoom?.locEast != null

        searchButton.isEnabled = currentRoom?.item != null

        inv1.text = if(app.hasKey1)"Key 1" else "Empty"
        inv2.text = if(app.hasKey2)"Key 2" else "Empty"
        inv3.text = if(app.hasKey3)"Key 3" else "Empty"


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
                    app.currentRoom = currentRoom.locNorth
                    app.updateClickCount()
                }

            }

            downButton -> {
                if (currentRoom?.locSouth != null) {
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
                    app.currentRoom = currentRoom.locEast
                    app.updateClickCount()
                }
            }

            searchButton -> {
                currentRoom?.item?.let {item ->
                    when(item){
                        "key 1" -> app.hasKey1 = true
                        "key 2" -> app.hasKey2 = true
                        "key 3" -> app.hasKey3 = true
                    }
                    app.currentRoom?.item = null
                    updateView()
                }
            }

        }
        updateView()
    }

}

