package io.github.skippi;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class AuraProvider implements ICapabilityProvider {
  private Aura instance = Aura.CAPABILITY.getDefaultInstance();

  @Override
  public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
    return capability == Aura.CAPABILITY;
  }

  @Override
  public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
    if (hasCapability(capability, facing)) {
      return Aura.CAPABILITY.cast(instance);
    } else {
      return null;
    }
  }
}
