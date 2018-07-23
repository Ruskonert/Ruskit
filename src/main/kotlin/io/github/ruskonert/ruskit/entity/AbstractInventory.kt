package io.github.ruskonert.ruskit.entity

import io.github.ruskonert.ruskit.entity.inventory.InventoryComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import java.util.*

abstract class AbstractInventory : SerializableEntity
{
    private constructor() : super(UUID.randomUUID().toString())

    protected constructor(inventoryName: String) : super(inventoryName)

    open fun initialize(tableRows: Int, inventoryName: String, clickSound: SoundEffect?, owner: Player? = null) {
        AbstractInventory.initialize(this, tableRows, inventoryName, clickSound, owner)
    }

    companion object
    {
        fun initialize(entity : AbstractInventory,
                       tableRows: Int = 1,
                       inventoryName: String,
                       clickSound: SoundEffect? = null,
                       owner: Player? = null) : AbstractInventory {
            val targetObject : AbstractInventory = entity
            for (i in 0..tableRows * 9) {
                targetObject.slotComponents[i] = null
                targetObject.soundEffects[i] = clickSound }
            targetObject.owner = owner
            targetObject.inventoryBase = Bukkit.createInventory(owner, tableRows * 9, inventoryName)
            return targetObject
        }
    }

    open fun open() { (owner!! as Player).openInventory(this.inventoryBase) }

    open fun open(p : Player) { p.openInventory(this.inventoryBase) }

    private var owner : InventoryHolder? = null
    fun getOwner() : InventoryHolder? = this.owner!!

    private var inventoryBase : Inventory? = null
    private var customizationName : String = "Custom Inventory"
    fun getInventoryName() = this.customizationName

    private val soundEffects : HashMap<Int, SoundEffect?> = HashMap()
    fun getSoundEffects() : HashMap<Int, SoundEffect?> = this.soundEffects
    fun hasSoundEffects(slot : Int) : Boolean = this.soundEffects[slot] != null

    private val slotComponents : HashMap<Int, InventoryComponent?> = HashMap()
    fun getSlotComponents() : HashMap<Int, InventoryComponent?> = this.slotComponents
    fun setComponent(slot : Int, component: InventoryComponent) {
        this.slotComponents[slot] = component
    }
}