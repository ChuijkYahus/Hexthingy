package me.uwutaku.hexthingy;

import at.petrak.hexcasting.api.casting.castables.Action;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.OperationResult;
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage;
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation;
import at.petrak.hexcasting.api.casting.mishaps.Mishap;

import java.util.Optional;
import java.util.function.Function;

public class ActionPredicateWrapper implements Action {

    private Action baseAction;
    private Function<CastingEnvironment, Optional<Mishap>> mishapDecider;

    public ActionPredicateWrapper(Action baseAction, Function<CastingEnvironment, Optional<Mishap>> mishapDecider){
        this.baseAction = baseAction;
        this.mishapDecider = mishapDecider;
    }

    public OperationResult operate(CastingEnvironment env, CastingImage image, SpellContinuation continuation){
        Optional<Mishap> maybeMishap = mishapDecider.apply(env);
        if(maybeMishap.isPresent()){
            MishapThrower.throwMishap(maybeMishap.get());
            return null; // will never reach
        }
        return baseAction.operate(env, image, continuation);
    }
}
