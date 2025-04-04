package org.rain.common.tree;

import lombok.Data;

import java.util.List;

/**
 * 要求，如果一个元素没有父元素，则pid = 0
 */
@Data
public abstract class BaseTree<T>  {
    private String id;
    private String pid;
    private List<T> children;
}
