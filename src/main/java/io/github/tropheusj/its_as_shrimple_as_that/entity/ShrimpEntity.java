package io.github.tropheusj.its_as_shrimple_as_that.entity;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ShrimpEntity extends PathfinderMob {
	public static final EntityDataAccessor<Optional<BlockPos>> WORKSTATION = SynchedEntityData.defineId(ShrimpEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);

	public ShrimpEntity(EntityType<? extends ShrimpEntity> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(Builder builder) {
		super.defineSynchedData(builder);
		builder.define(WORKSTATION, Optional.empty());
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FollowDreamsGoal(this, 1, 16, 2));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10, 0.1f));
	}

	public void becomeChef(BlockPos workstation) {
		this.entityData.set(WORKSTATION, Optional.of(workstation));
	}

	public void crushDreams() {
		this.entityData.set(WORKSTATION,Optional.empty());
	}

	public boolean isChef() {
		return this.entityData.get(WORKSTATION).isPresent();
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8);
	}
}
