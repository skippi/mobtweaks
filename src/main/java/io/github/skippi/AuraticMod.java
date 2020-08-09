package io.github.skippi;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod(modid = AuraticMod.MOD_ID, name = AuraticMod.MOD_ID, version = "1.0", acceptableRemoteVersions = "*")
public class AuraticMod {
  public static final String MOD_ID = "auratic";

  @EventHandler
  public void init(FMLInitializationEvent event) {
    CapabilityManager.INSTANCE.register(Aura.class, new NullStorage<>(), Aura::new);
    MinecraftForge.EVENT_BUS.register(this);
  }

  @SubscribeEvent
  public void attachAura(AttachCapabilitiesEvent<Entity> event) {
    Entity entity = event.getObject();
    if (entity.world.isRemote) return;
    if (!(entity instanceof EntityMob)) return;
    Random random = entity.getEntityWorld().rand;
    if (random.nextDouble() < 0.05) {
      event.addCapability(Aura.RESOURCE, new AuraProvider());
    }
  }

  @SubscribeEvent
  public void tickAura(LivingEvent.LivingUpdateEvent event) {
    Aura aura = event.getEntity().getCapability(Aura.CAPABILITY, null);
    if (aura != null) {
      aura.cooldownTicks = (aura.cooldownTicks + 1) % 5;
      if (aura.cooldownTicks != 0) return;
      evaporate(event.getEntity().world, new AxisAlignedBB(event.getEntity().getPosition()).grow(3));
    }
  }

  private void evaporate(World world, AxisAlignedBB area) {
    for (int i = (int) area.minX; i <= area.maxX; ++i) {
      for (int j = (int) area.minY; j <= area.maxY; ++j) {
        for (int k = (int) area.minZ; k <= area.maxZ; ++k) {
          evaporate(world, new BlockPos(i, j, k));
        }
      }
    }
  }

  private void evaporate(World world, BlockPos pos) {
    Block block = world.getBlockState(pos).getBlock();
    if (block instanceof BlockLiquid) {
      world.setBlockToAir(pos);
    }
  }
}
