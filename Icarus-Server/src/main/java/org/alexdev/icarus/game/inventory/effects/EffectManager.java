package org.alexdev.icarus.game.inventory.effects;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.user.effects.EffectExpiredMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.effects.EffectsMessageComposer;
import org.alexdev.icarus.messages.types.MessageComposer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EffectManager {

    private static final int EFFECT_TIME_IN_SECONDS = 3 * 60; // 10 minutes... should really add this to config somewhere ;)

    private final Player player;
    private Map<Integer, Effect> effects;

    public EffectManager(Player player) {
        this.player = player;
        this.effects = new HashMap<>(); // TODO: Fetch user effect data from database
    }

    /**
     * Add or increment the time which the effect lasts and return its instance.
     *
     * @param effectId the effect id
     * @return the effect
     */
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

    /**
     * Deactivates all effects using a very hacky method.
     */
    public void deactivateEffects() {
        List<Effect> activatedEffects = this.getActivatedEffects();

        for (Effect activatedEffect : activatedEffects) {
            activatedEffect.setActivated(false);
            this.player.sendQueued(new EffectExpiredMessageComposer(activatedEffect));
            this.player.sendQueued(new EffectsMessageComposer(activatedEffect));
        }

        this.player.flushQueue();
        this.player.getRoomUser().applyEffect(0);
    }

    /**
     * Get effect instance.
     *
     * @param effectId the effect id
     * @return the effect
     */
    public Effect getEffect(int effectId) {
        if (this.effects.containsKey(effectId)) {
            return this.effects.get(effectId);
        }

        return null;
    }

    /**
     * Get list of activated effects.
     *
     * @return list of effects
     */
    public List<Effect> getActivatedEffects() {
        List<Effect> effects = new ArrayList<>();

        for (Effect effect : this.effects.values()) {

            if (!effect.isActivated()) {
                continue;
            }

            effects.add(effect);
        }

        return effects;
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
