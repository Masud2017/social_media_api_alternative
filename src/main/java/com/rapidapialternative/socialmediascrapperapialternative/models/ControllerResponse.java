package com.rapidapialternative.socialmediascrapperapialternative.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class ControllerResponse {
    private Map<String,Integer> playCount;
}
