package ua.epam.spring.hometask.domain;

import lombok.Getter;

/**
 * @author Yuriy_Tkach
 */
@Getter
public enum EventRating {

    LOW(0.8), MID(1.0), HIGH(1.2);

    private double ratingCoeff;

    EventRating(double rating) {
        this.ratingCoeff = rating;
    }
}
