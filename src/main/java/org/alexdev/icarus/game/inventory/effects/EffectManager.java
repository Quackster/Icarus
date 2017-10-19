package org.alexdev.icarus.game.inventory.effects;

import org.alexdev.icarus.messages.outgoing.user.effects.EffectsMessageComposer;
import org.alexdev.icarus.messages.types.MessageComposer;

import java.util.HashMap;
import java.util.Map;

public class EffectManager {

    private static final int EFFECT_TIME_IN_SECONDS = 10 * 60; // 10 minutes... should really add this to config somewhere ;)

    private Map<Integer, Effect> effects;

    public EffectManager(int entityId) {
        this.effects = new HashMap<>(); // TODO: Fetch user effect data from database
    }

    public Effect addEffect(int effectId) {

        boolean effectExists = this.effects.containsKey(effectId);

        int quantity = 1;
        int timeLeft = EFFECT_TIME_IN_SECONDS;

        if (effectExists) {
            Effect oldInstance = this.getEffect(effectId);
            quantity += oldInstance.getQuantity().get();
            timeLeft += oldInstance.getTimeLeft().get();
        }

        Effect effect = new Effect(effectId, false, timeLeft, quantity);
        this.effects.put(effect.getEffectId(), effect);

        return effect;
    }

    public Effect getEffect(int effectId) {

        if (this.effects.containsKey(effectId)) {
            return this.effects.get(effectId);
        }

        return null;
    }

    public Effect getActivatedEffect() {

        for (Effect effect : this.effects.values()) {

            if (!effect.isActivated()) {
                continue;
            }

            return effect;
        }

        return null;
    }

    /**
     * Create effects composer.
     *
     * @return the message composer
     */
    public MessageComposer createEffectsComposer() {
        return new EffectsMessageComposer(this.effects.values());
    }

    /**
     * Create effect composer.
     *
     * @return the message composer
     */
    public MessageComposer createEffectComposer(Effect effect) {
        return new EffectsMessageComposer(effect);
    }

    /**
     * Get list of effects
     *
     * @return list of effects
     */
    public Map<Integer, Effect> getEffects() {
        return effects;
    }
}
