package me.uwutaku.hexthingy

import at.petrak.hexcasting.api.addldata.ADMediaHolder
import at.petrak.hexcasting.api.casting.*
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.utils.scanPlayerForMediaStuff
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.registry.RegistryKeys
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld


class Smite : SpellAction {
    override val argc = 2

    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): SpellAction.Result {
        val target = args.getLivingEntityButNotArmorStand(0)
        val dmg = args.getDouble(1)
        env.assertEntityInRange(target)
        val cost = dmg * MediaConstants.SHARD_UNIT * 1.5
        return SpellAction.Result(
            Spell(target, dmg, env.castingEntity!!),
            cost.toLong(),
            listOf(ParticleSpray.cloud(target.pos.add(0.0, target.eyeY / 2.0, 0.0), 1.0))
        )
    }

    private class Spell(val target: LivingEntity, val dmg: Double, val caster: LivingEntity) :
        RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            if (target.world is ServerWorld) {
                val caster_media_amount = Hexthingy.getTotalMediaFromEntity(caster)
                val target_media_amount = Hexthingy.getTotalMediaFromEntity(target)
                
                val total_media = caster_media_amount + target_media_amount
                var caster_damage = (target_media_amount / total_media) * dmg.toFloat()
                var target_damage = (caster_media_amount / total_media) * dmg.toFloat()

                caster.damage(DamageTypes.of(caster.world, DamageTypes.smitecaster_damage), caster_damage)
                target.damage(DamageTypes.of(target.world, DamageTypes.smiterecipient_damage), target_damage)
            }
        }
    }
}