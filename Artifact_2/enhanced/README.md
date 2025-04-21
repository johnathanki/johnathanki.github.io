# Enhanced Inventory Management App (Standalone Android Application)
This enhanced version of the inventory management app makes changes to the data structure of inventory items and introduces sorting algorithms improve item organization and the user experience.


### Features
- Updated data structure for inventory item
- Sort options in ASC/DESC order using `Comparator` and `Collections` for:
	- Item name
	- Quantity
	- Price
	- Date added
	- Date updated
- Updated UI to add sort button
- Adjusted UI to smoothly update when a sort is applied

Sorting options were added into [com.zybooks.inventoryapp.viewmodel.InventoryViewModel](https://github.com/johnathanki/johnathanki.github.io/blob/main/Artifact_2/enhanced/InventoryApp/app/src/main/java/com/zybooks/inventoryapp/viewmodel/InventoryViewModel.java)

The UI for sorting was added using `PopupMenu` and `Menu`.


[Original Inventory Management App](https://github.com/johnathanki/johnathanki.github.io/blob/main/Artifact_2/original/README.md)

A narrative for this enhancement can be found here: [CS-499 Milestone Three Narrative](https://github.com/johnathanki/johnathanki.github.io/blob/main/Artifact_2/CS-499%20Milestone%20Three%20Narrative.docx)


