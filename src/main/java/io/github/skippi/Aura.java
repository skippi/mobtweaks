package io.github.skippi;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class Aura {
  public int cooldownTicks = 0;

  @CapabilityInject(Aura.class)
  public static Capability<Aura> CAPABILITY;
  public static ResourceLocation RESOURCE = new ResourceLocation(AuraticMod.MOD_ID, "aura");
}
