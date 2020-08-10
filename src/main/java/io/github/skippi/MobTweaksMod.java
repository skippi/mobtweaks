package io.github.skippi;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

@Mod(modid = MobTweaksMod.MOD_ID, name = MobTweaksMod.MOD_ID, version = "1.0", acceptableRemoteVersions = "*")
public class MobTweaksMod {
  public static final String MOD_ID = "mobtweaks";

  public static final AttributeModifier LAND_SPEED_BOOST = new AttributeModifier(
    UUID.fromString("078e4fc9-8fd4-425e-b2a7-7b958a1b7dbe"),
    "Land speed boost",
    0.3,
    2
  ).setSaved(false);
  public static final AttributeModifier WATER_SPEED_BOOST = new AttributeModifier(
    UUID.fromString("eba2f38b-4a27-42e7-9a2f-0ae1fdbbd4a8"),
    "Water speed boost",
    3,
    2
  ).setSaved(false);
  public static final AttributeModifier WATER_SWIM_BOOST = new AttributeModifier(
    UUID.fromString("45d116dd-f203-4cbb-9065-4de7440ae653"),
    "Water swim boost",
    2,
    2
  ).setSaved(false);

  @EventHandler
  public void init(FMLInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(this);
  }

  @SubscribeEvent
  public void updateSpeedBoosts(LivingEvent.LivingUpdateEvent event) {
    EntityLivingBase entity = event.getEntityLiving();
    if (!(entity instanceof EntityMob)) return;
    IAttributeInstance swimSpeed = entity.getEntityAttribute(EntityLivingBase.SWIM_SPEED);
    IAttributeInstance moveSpeed = entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
    if (entity.isInWater()) {
      apply(WATER_SPEED_BOOST, moveSpeed);
      apply(WATER_SWIM_BOOST, swimSpeed);
      remove(LAND_SPEED_BOOST, moveSpeed);
    } else {
      remove(WATER_SPEED_BOOST, moveSpeed);
      remove(WATER_SWIM_BOOST, swimSpeed);
      apply(LAND_SPEED_BOOST, moveSpeed);
    }
  }

  private void apply(AttributeModifier modifier, IAttributeInstance attribute) {
    if (!attribute.hasModifier(modifier)) {
      attribute.applyModifier(modifier);
    }
  }

  private void remove(AttributeModifier modifier, IAttributeInstance attribute) {
    if (attribute.hasModifier(modifier)) {
      attribute.removeModifier(modifier);
    }
  }
}
