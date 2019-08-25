package de.flo56958.MineTinker.Modifiers.Types;

import de.flo56958.MineTinker.Data.ToolType;
import de.flo56958.MineTinker.Main;
import de.flo56958.MineTinker.Modifiers.Modifier;
import de.flo56958.MineTinker.Utilities.ConfigurationManager;
import de.flo56958.MineTinker.Utilities.nms.NBTUtils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.*;

public class Haste extends Modifier {

    private static Haste instance;
    private double attackSpeedPerLevel;

    public static Haste instance() {
        synchronized (Haste.class) {
            if (instance == null) {
                instance = new Haste();
            }
        }

        return instance;
    }

    @Override
    public String getKey() {
        return "Haste";
    }

    @Override
    public List<ToolType> getAllowedTools() {
        return Arrays.asList(ToolType.AXE, ToolType.CROSSBOW, ToolType.SWORD, ToolType.TRIDENT, ToolType.PICKAXE, ToolType.SHOVEL, ToolType.SHEARS, ToolType.FISHINGROD);
    }

    private Haste() {
        super(Main.getPlugin());
    }

    @Override
    public List<Enchantment> getAppliedEnchantments() {
        if (NBTUtils.isOneFourteenCompatible()) {
            return Arrays.asList(Enchantment.LURE, Enchantment.DIG_SPEED, Enchantment.QUICK_CHARGE);
        }

        return Arrays.asList(Enchantment.LURE, Enchantment.DIG_SPEED);
    }

    @Override
    public List<Attribute> getAppliedAttributes() {
        return Collections.singletonList(Attribute.GENERIC_ATTACK_SPEED);
    }

    @Override
    public void reload() {
    	FileConfiguration config = getConfig();
    	config.options().copyDefaults(true);

    	config.addDefault("Allowed", true);
    	config.addDefault("Name", "Haste");
    	config.addDefault("ModifierItemName", "Compressed Redstoneblock");
        config.addDefault("Description", "Tool can destroy blocks faster!");
        config.addDefault("DescriptionModifierItem", "%WHITE%Modifier-Item for the Haste-Modifier");
        config.addDefault("Color", "%DARK_RED%");
        config.addDefault("MaxLevel", 5);
        config.addDefault("OverrideLanguagesystem", false);
        config.addDefault("AttackSpeedPerLevel", 0.05);

        config.addDefault("EnchantCost", 10);
        config.addDefault("Enchantable", false);

    	config.addDefault("Recipe.Enabled", true);
    	config.addDefault("Recipe.Top", "RRR");
    	config.addDefault("Recipe.Middle", "RRR");
    	config.addDefault("Recipe.Bottom", "RRR");

        Map<String, String> recipeMaterials = new HashMap<>();
        recipeMaterials.put("R", Material.REDSTONE_BLOCK.name());

        config.addDefault("Recipe.Materials", recipeMaterials);

    	ConfigurationManager.saveConfig(config);
        ConfigurationManager.loadConfig("Modifiers" + File.separator, getFileName());

        init(Material.REDSTONE_BLOCK, true);

        this.attackSpeedPerLevel = config.getDouble("AttackSpeedPerLevel", 0.05);
    }

    @Override
    public boolean applyMod(Player p, ItemStack tool, boolean isCommand) {
        ItemMeta meta = tool.getItemMeta();

        if (meta != null) {
            if (meta.getAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE) == null || meta.getAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE).isEmpty()) modManager.addToolAttributes(tool);
            if (ToolType.FISHINGROD.contains(tool.getType())) {
                meta.addEnchant(Enchantment.LURE, modManager.getModLevel(tool, this), true);
            } else if (ToolType.CROSSBOW.contains(tool.getType())) {
                if (NBTUtils.isOneFourteenCompatible()) meta.addEnchant(Enchantment.QUICK_CHARGE, modManager.getModLevel(tool, this), true);
            } else if (ToolType.SWORD.contains(tool.getType()) || ToolType.TRIDENT.contains(tool.getType())) {
                meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed",
                        attackSpeedPerLevel * modManager.getModLevel(tool, this), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            } else if (ToolType.AXE.contains(tool.getType())) {
                meta.addEnchant(Enchantment.DIG_SPEED, modManager.getModLevel(tool, this), true);
                meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED);
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed",
                        attackSpeedPerLevel * modManager.getModLevel(tool, this), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            } else {
                meta.addEnchant(Enchantment.DIG_SPEED, modManager.getModLevel(tool, this), true);
            }

            tool.setItemMeta(meta);
        }
        return true;
    }
}
