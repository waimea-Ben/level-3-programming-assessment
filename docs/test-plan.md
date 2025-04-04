# Plan for Testing the Program

The test plan lays out the actions and data I will use to test the functionality of my program.

Terminology:

- **VALID** data values are those that the program expects
- **BOUNDARY** data values are at the limits of the valid range
- **INVALID** data values are those that the program should reject

---

## Valid Movement - Move North from Entrance

This test checks whether a player can move north from the Entrance Hall to the next room, as allowed by the room data. This ensures that VALID directional input is handled properly and that the player position updates correctly in the UI and internal logic.

### Test Data To Use

Start in the "Entrance Hall" and press the **North (N)** button. This is a VALID move since the north location of this room is set to "Woodland Mansion".

### Expected Test Result

The current room should successfully update to "Woodland Mansion". The `clicks` value should increase by 1 to track the move. The compass label or direction indicator (if present) should also update accordingly.

---

## Invalid Movement - Attempt to Move North from Ruined Temple Without Key

This test checks that movement is blocked when trying to go in a direction that requires a key item. This ensures that the program correctly enforces room-specific item checks for movement (INVALID action without required item).

### Test Data To Use

Set the `currentRoom` to "Ruined Temple" and ensure `hasKey3` is **false**. Press the **North (N)** button to attempt to enter the locked room.

### Expected Test Result

The room should not change, and a dialog message should appear informing the user that the gold key is required. The `clicks` value should **not** increase.

---

## Boundary Test - Maximum Moves

This test checks that the player cannot exceed the maximum number of moves allowed in the game (a BOUNDARY value test). It ensures the game correctly enforces movement limits.

### Test Data To Use

Manually set the move counter (`clicks`) to 50, which is the defined move limit. Attempt any move by clicking a direction button.

### Expected Test Result

The room should remain the same. A dialog or indicator should appear showing the player cannot move further. The `clicks` counter should stay at 50.

---

## Valid Item Pickup - Search Room for Key

This test checks that the player can find and pick up a key item when searching a room that contains one. This tests VALID search functionality and inventory update.

### Test Data To Use

Set the current room to "Cave", which contains key1. Press the **Search** button.

### Expected Test Result

The player should receive a dialog stating they found a key. The value `hasKey1` should be set to **true** and the item should be removed from the room to prevent duplicate pickup.

---

## Invalid Search - Search Room With No Items

This test checks that searching a room without any item does not cause a crash or unexpected behavior. It ensures INVALID item search scenarios are handled gracefully.

### Test Data To Use

Set the current room to "Forest", which does not have any item. Press the **Search** button.

### Expected Test Result

No item should be added to inventory. A default message should appear (if coded), or no dialog if that’s the expected behavior. Program should continue to function normally.

---

## Valid Compass Pickup - Display Compass Indicator

This test checks that collecting the compass updates both internal state and UI display. It ensures that special item pickup (VALID) works correctly.

### Test Data To Use

Set the current room to "Entrance Hall" and press the **Search** button, where the compass is located.

### Expected Test Result

The value `hasCompass` should be set to **true**. A dialog should appear confirming the pickup. The compass label in the UI should update (e.g., show cardinal directions or highlight the new direction).

---

## Invalid Movement - Blocked by Missing Totem

This test checks movement prevention logic when the required item is missing. It ensures that the room correctly prevents movement without a necessary item (INVALID action).

### Test Data To Use

Set the `currentRoom` to "Dark Dungeon", set `hasKey2` to **false**, and attempt to move East.

### Expected Test Result

A dialog should appear saying movement is blocked. The player should remain in the same room. The `clicks` counter should not increase.

---