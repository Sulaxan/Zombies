package me.sulaxan.zombies.util.component;

import lombok.Getter;

import java.awt.*;

@Getter
public abstract class GenericComponent extends Component {

    private int id;

    public void setId(int id) {
        if(id != 0) {
            this.id = id;
        }
    }

    public abstract void tick();

    public abstract void render(Graphics graphics);
}
