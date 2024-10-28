package suggest.taste_the_weather.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRegion is a Querydsl query type for Region
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRegion extends EntityPathBase<Region> {

    private static final long serialVersionUID = -1213811936L;

    public static final QRegion region = new QRegion("region");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> nx = createNumber("nx", Integer.class);

    public final NumberPath<Integer> ny = createNumber("ny", Integer.class);

    public final StringPath regionChild = createString("regionChild");

    public final StringPath regionParent = createString("regionParent");

    public QRegion(String variable) {
        super(Region.class, forVariable(variable));
    }

    public QRegion(Path<? extends Region> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRegion(PathMetadata metadata) {
        super(Region.class, metadata);
    }

}

