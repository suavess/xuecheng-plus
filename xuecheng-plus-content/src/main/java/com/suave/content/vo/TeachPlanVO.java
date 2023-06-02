package com.suave.content.vo;

import com.suave.content.entity.Teachplan;
import com.suave.content.entity.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

/**
 * @author Suave
 * @since 2023/06/01 22:20
 */
@Data
@ToString
public class TeachPlanVO extends Teachplan {

    private TeachplanMedia teachplanMedia;
}
