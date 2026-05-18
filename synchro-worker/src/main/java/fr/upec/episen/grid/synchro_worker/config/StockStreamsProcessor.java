package fr.upec.episen.grid.synchro_worker.config;

import fr.upec.episen.grid.synchro_worker.model.CommandeCreeeEvent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.kafka.support.serializer.JsonSerde;

@Component
public class StockStreamsProcessor {

    @Autowired
    public void buildPipeline(StreamsBuilder streamsBuilder) {

        JsonSerde<CommandeCreeeEvent> eventSerde = new JsonSerde<>(CommandeCreeeEvent.class);
        eventSerde.deserializer().setUseTypeHeaders(false);
        eventSerde.deserializer().addTrustedPackages("*");

        KStream<String, CommandeCreeeEvent> sourceStream = streamsBuilder.stream(
                "flux_commandes",
                Consumed.with(Serdes.String(), eventSerde));

        sourceStream
                .groupBy((key, event) -> event.typePain(), Grouped.with(Serdes.String(), eventSerde))
                .aggregate(
                        () -> 0, // Valeur par défaut
                        (key, event, valeurAgregee) -> valeurAgregee - event.quantite(), // Soustraction
                        Materialized.with(Serdes.String(), Serdes.Integer()))
                .toStream()
                .to("stock_calcule", Produced.with(Serdes.String(), Serdes.Integer()));
    }
}