package org.acme.resource;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.acme.entity.Fruit;

@Path("/fruits")
public class FruitResource {

    private Set<Fruit> fruits = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public FruitResource() {
        fruits.add(new Fruit("Maçã", "Fruta do inverno", 1L));
        fruits.add(new Fruit("Abacaxi", "Fruta tropical", 2L));
    }

    @GET
    public Set<Fruit> list() {
        return fruits;
    }

    @POST
    public Set<Fruit> add(Fruit fruit) {
        fruits.add(fruit);
        return fruits;
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        // fruits.removeIf(existingFruit -> existingFruit.nome.contentEquals(fruit.nome));
        fruits.removeIf(existingFruit -> existingFruit.id.equals(id));
        return Response.noContent().build(); // "constroi" um response com status 200.
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Fruit fruit) {
        Fruit fru = fruits.stream().filter(f -> f.id.equals(id)).findAny().orElse(null);
        fru.nome = fruit.nome;
        fru.descricao = fruit.descricao;

        return Response.ok().entity(fru).build();
    }

   /* @PATCH                                            **** TO DO *****
    @Path("/{id}")
    public Response partial(@PathParam("id") Long id, List<Fruit> fruits) {
        if (fruits == null || fruits.size() == 0) {
            throw new WebApplicationException("Lista vazia", 422);
        }

        if(fruits.contains(name)) {
            Fruit f = fruits.get(0);
            f.descricao = "descricao atualizada!";
            fruits.add(f);
        }
        return Fruit;
    } */
}