package me.uwutaku.hexthingy

import at.petrak.hexcasting.api.casting.*
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect

class CleanEffect : SpellAction {
    override val argc = 1

    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): SpellAction.Result {
        val target = args.getLivingEntityButNotArmorStand(0)
        var cost = 0.0
        var effects : ArrayList<StatusEffect> = arrayListOf()
        for (effect in target.statusEffects){
            if(!effect.isInfinite) {
                effects.add(effect.effectType)
                cost += effect.duration * effect.amplifier * MediaConstants.DUST_UNIT
            }
        }
        return SpellAction.Result(
            Spell(target, effects),
            cost.toLong(),
            listOf(ParticleSpray.cloud(target.pos.add(0.0, target.eyeY / 2.0, 0.0), 1.0))
        )
    }

    private class Spell(val target: LivingEntity, val effects : ArrayList<StatusEffect>) :
        RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            for (effect in effects) {
                target.removeStatusEffect(effect)
            }
        }
    }
}