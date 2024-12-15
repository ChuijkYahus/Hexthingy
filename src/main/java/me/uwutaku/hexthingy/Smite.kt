package me.uwutaku.hexthingy

import at.petrak.hexcasting.api.casting.*
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import net.minecraft.entity.LivingEntity
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
            Spell(target, dmg),
            cost.toLong(),
            listOf(ParticleSpray.cloud(target.pos.add(0.0, target.eyeY / 2.0, 0.0), 1.0))
        )
    }

    private class Spell(val target: LivingEntity, val dmg: Double) :
        RenderedSpell {
        override fun cast(env: CastingEnvironment) {

            if (target.world is ServerWorld) {
                val serverWorld = target.world as ServerWorld
                val magicDamage = serverWorld.damageSources.magic()
                target.damage(magicDamage, dmg.toFloat())
            }
        }
    }
}