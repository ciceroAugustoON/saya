package com.backend.saya.services;

import com.backend.saya.entities.Habit;
import com.backend.saya.entities.User;
import com.backend.saya.entities.enumeration.Archetype;
import com.backend.saya.entities.enumeration.Segmentation;
import com.backend.saya.util.MathUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ArchetypeService {

    public void defineArchetype(User user) {
        List<Habit> habitsHad = user.getObjectives().getHabitsHad();
        Segmentation[] segmentations = Segmentation.values();
        int[] counterSegmentation = new int[segmentations.length];
        for (Habit h : habitsHad) {
            Segmentation s = h.getSegmentation();
            int index = getSegmentationIndex(segmentations, s);
            counterSegmentation[index] += 1;
        }
        int maxIndex = MathUtils.findMaxValueIndex(counterSegmentation);
        if (maxIndex == -1) {
            user.setArchetype(Archetype.DEFAULT);
            return;
        }
        user.setArchetype(getArchetypeBySegmentation(segmentations[maxIndex]));
    }

    private int getSegmentationIndex(Segmentation[] segmentations, Segmentation segmentation) {
        for(int i = 0; i < segmentations.length; i++) {
            if (segmentations[i].equals(segmentation)) {
                return i;
            }
        }
        return -1;
    }

    private Archetype getArchetypeBySegmentation(Segmentation segmentation) {
        if (segmentation.equals(Segmentation.PHYSICAL_ACTIVITY)) {
            return Archetype.ATHLETE;
        }
        if (segmentation.equals(Segmentation.INTELLECTUAL_ACTIVITY) || segmentation.equals(Segmentation.LEISURE)) {
            return Archetype.ERUDITE;
        }
        if (segmentation.equals(Segmentation.MEDITATION)) {
            return Archetype.MEDITATOR;
        }
        if (segmentation.equals(Segmentation.HEALTH_CARE)) {
            return Archetype.WELLNESS;
        }
        return Archetype.DEFAULT;
    }

}
