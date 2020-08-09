package io.github.skippi;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = "auratic", name = "auratic", version = "1.0", acceptableRemoteVersions = "*")
public class AuraticMod {
  @EventHandler
  public void init(FMLInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(this);
  }

  @SubscribeEvent
  public void tickAura(LivingEvent.LivingUpdateEvent event) {
    if (event.getEntity() instanceof EntityPigZombie) {
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
