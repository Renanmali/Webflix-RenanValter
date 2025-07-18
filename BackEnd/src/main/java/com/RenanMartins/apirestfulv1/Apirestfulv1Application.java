package com.RenanMartins.apirestfulv1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.RenanMartins.apirestfulv1.model.Cliente;
import com.RenanMartins.apirestfulv1.model.Copia;
import com.RenanMartins.apirestfulv1.model.Filme;
import com.RenanMartins.apirestfulv1.model.Genero;
import com.RenanMartins.apirestfulv1.model.StatusCopia;
import com.RenanMartins.apirestfulv1.repository.ClienteRepository;
import com.RenanMartins.apirestfulv1.repository.CopiaRepository;
import com.RenanMartins.apirestfulv1.repository.FilmeRepository;
import com.RenanMartins.apirestfulv1.repository.GeneroRepository;

@SpringBootApplication
public class Apirestfulv1Application implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private CopiaRepository copiaRepository;

    public static void main(String[] args) {
        SpringApplication.run(Apirestfulv1Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Cliente admin = new Cliente("admin", "desweb");
        clienteRepository.save(admin);

        Genero acao = new Genero("Ação", "acao");
        Genero comedia = new Genero("Comédia", "comedia");
        Genero drama = new Genero("Drama", "drama");
        generoRepository.save(acao);
        generoRepository.save(comedia);
        generoRepository.save(drama);

        Filme filme1 = criarFilme("Corrida Explosiva", "corrida-explosiva", "corrida.png",
                "Pilotos arriscam tudo em uma corrida clandestina.", "J. Action", 2020, acao);
        Filme filme2 = criarFilme("Risos Garantidos", "risos-garantidos", "risos.png",
                "Uma família se mete em confusões hilárias nas férias.", "L. Funny", 2021, comedia);
        Filme filme3 = criarFilme("Caminhos do Coração", "caminhos-do-coracao", "coracao.png",
                "Um drama emocionante sobre superação.", "D. Tears", 2019, drama);

        filmeRepository.save(filme1);
        filmeRepository.save(filme2);
        filmeRepository.save(filme3);

        criarCopias(filme1, 2);
        criarCopias(filme2, 2);
        criarCopias(filme3, 2);
    }

    private Filme criarFilme(String titulo, String slug, String imagem, String sinopse,
                             String diretor, int anoLancamento, Genero genero) {
        Filme filme = new Filme();
        filme.setTitulo(titulo);
        filme.setSlug(slug);
        filme.setImagem(imagem);
        filme.setSinopse(sinopse);
        filme.setDiretor(diretor);
        filme.setAnoLancamento(anoLancamento);
        filme.setGenero(genero);
        return filme;
    }

    private void criarCopias(Filme filme, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            Copia copia = new Copia();
            copia.setFilme(filme);
            copia.setStatus(StatusCopia.DISPONIVEL);
            copiaRepository.save(copia);
        }
    }
}

