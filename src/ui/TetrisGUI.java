package ui;

import model.*;
import service.SalvadorEstado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TetrisGUI extends JFrame {

    private final int CELL_SIZE = 32;
    private Tabuleiro tabuleiro;
    private Tetromino pecaAtual;
    private Tetromino proximaPeca;
    private SistemaPontuacao sistemaPontuacao;
    private int pontuacao, nivel, totalLinhas;
    private Timer timer;
    private Random random = new Random();

    public TetrisGUI() {
        setTitle("Tetris Java");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(Tabuleiro.LARGURA * CELL_SIZE + 210, Tabuleiro.ALTURA * CELL_SIZE + 50);
        setLocationRelativeTo(null);

        tabuleiro = new Tabuleiro();
        sistemaPontuacao = new SistemaPontuacao();
        pontuacao = 0;
        nivel = 1;
        totalLinhas = 0;
        pecaAtual = novaPeca();
        proximaPeca = novaPeca();

        add(new TabuleiroPanel());
        inicializarTimer();
        inicializarTeclado();

        setVisible(true);
    }

    private Tetromino novaPeca() {
        TipoTetromino[] tipos = TipoTetromino.values();
        return new Tetromino(tipos[random.nextInt(tipos.length)], new Posicao(3, 0));
    }

    private void inicializarTimer() {
        timer = new Timer((int)sistemaPontuacao.calcularVelocidade(nivel), e -> moverBaixo());
        timer.start();
    }

    private void inicializarTeclado() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean repaint = true;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT: moverEsquerda(); break;
                    case KeyEvent.VK_RIGHT: moverDireita(); break;
                    case KeyEvent.VK_UP: rotacionar(); break;
                    case KeyEvent.VK_DOWN: moverBaixo(); break;
                    case KeyEvent.VK_S: salvarPartida("tetris_save"); break;
                    case KeyEvent.VK_C: carregarPartida("tetris_save"); break;
                    default: repaint = false;
                }
                if (repaint) repaint();
            }
        });
        setFocusable(true);
    }

    private void moverEsquerda() {
        pecaAtual.mover(-1, 0);
        if (!tabuleiro.posicaoValida(pecaAtual)) pecaAtual.mover(1, 0);
    }

    private void moverDireita() {
        pecaAtual.mover(1, 0);
        if (!tabuleiro.posicaoValida(pecaAtual)) pecaAtual.mover(-1, 0);
    }

    private void rotacionar() {
        pecaAtual.rotacionar();
        if (!tabuleiro.posicaoValida(pecaAtual))
            for (int i = 0; i < 3; i++) pecaAtual.rotacionar();
    }

    private void moverBaixo() {
        pecaAtual.mover(0, 1);
        if (!tabuleiro.posicaoValida(pecaAtual)) {
            pecaAtual.mover(0, -1);
            tabuleiro.fixarTetromino(pecaAtual);
            processarLinhas();
            Tetromino nova = proximaPeca;
            proximaPeca = novaPeca();
            if (!tabuleiro.posicaoValida(nova)) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Fim do jogo! Pontuação: " + pontuacao);
                return;
            }
            pecaAtual = nova;
        }
        repaint();
    }

    private void processarLinhas() {
        int linhas = tabuleiro.eliminarLinhasCompletas();
        if (linhas > 0) {
            totalLinhas += linhas;
            pontuacao += sistemaPontuacao.calcularPontos(linhas, nivel);
            nivel = sistemaPontuacao.calcularNovoNivel(totalLinhas);
            timer.setDelay((int)sistemaPontuacao.calcularVelocidade(nivel));
        }
    }

    private Color corParaTipo(TipoTetromino tipo) {
        return switch (tipo) {
            case I -> new Color(38, 255, 255);
            case O -> Color.YELLOW;
            case T -> new Color(190, 51, 255);
            case S -> new Color(51, 255, 102);
            case Z -> new Color(255, 38, 38);
            case J -> new Color(51, 102, 255);
            case L -> new Color(255, 136, 38);
            default -> Color.GRAY;
        };
    }

    private void salvarPartida(String nomeArquivo) {
        try {
            Partida partida = new Partida(
                java.util.UUID.randomUUID().toString(),
                "default", pontuacao, totalLinhas, nivel,
                pecaAtual.getTipo().name(), tabuleiro, false, System.currentTimeMillis()
            );
            SalvadorEstado.salvarPartida(partida, nomeArquivo);
            JOptionPane.showMessageDialog(this, "Partida salva!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }

    private void carregarPartida(String nomeArquivo) {
        try {
            Partida partida = SalvadorEstado.carregarPartida(nomeArquivo);
            pontuacao = partida.getPontuacao();
            totalLinhas = partida.getTotalLinhas();
            nivel = partida.getNivel();
            tabuleiro = partida.getTabuleiro();
            pecaAtual = new Tetromino(TipoTetromino.valueOf(partida.getTipoPecaAtual()), new Posicao(3, 0));
            timer.setDelay((int)sistemaPontuacao.calcularVelocidade(nivel));
            JOptionPane.showMessageDialog(this, "Partida carregada!");
            repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar: " + e.getMessage());
        }
    }

    class TabuleiroPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            // Fundo gradiente
            GradientPaint fundo = new GradientPaint(0, 0, new Color(224, 224, 255), getWidth()/2, getHeight(), Color.WHITE);
            g2.setPaint(fundo);
            g2.fillRect(0, 0, getWidth(), getHeight());

            // Desenha grid estilizada e blocos fixos com gradiente/sombra
            for (int y = 0; y < Tabuleiro.ALTURA; y++)
                for (int x = 0; x < Tabuleiro.LARGURA; x++)
                    if (tabuleiro.temBloco(x, y))
                        desenharBloco(g2, x, y, Color.DARK_GRAY);

            // Peça atual estilizada
            boolean[][] forma = pecaAtual.getForma();
            Posicao pos = pecaAtual.getPosicao();
            Color corAtual = corParaTipo(pecaAtual.getTipo());
            for (int i = 0; i < forma.length; i++)
                for (int j = 0; j < forma[i].length; j++)
                    if (forma[i][j])
                        desenharBloco(g2, pos.getX() + j, pos.getY() + i, corAtual);

            // Próxima peça ao lado, estilizada
            boolean[][] proxForma = proximaPeca.getForma();
            Color corProx = corParaTipo(proximaPeca.getTipo());
            int xOffset = Tabuleiro.LARGURA * CELL_SIZE + 32;
            int yOffset = 80;
            for (int i = 0; i < proxForma.length; i++)
                for (int j = 0; j < proxForma[i].length; j++)
                    if (proxForma[i][j])
                        desenharBloco(g2, xOffset/CELL_SIZE + j, yOffset/CELL_SIZE + i, corProx);

            // HUD com fonte grande e sombra
            g2.setFont(new Font("Segoe UI", Font.BOLD, 24));
            g2.setColor(new Color(60,60,120));
            g2.drawString("Pontuação: " + pontuacao, Tabuleiro.LARGURA * CELL_SIZE + 28, 60);
            g2.drawString("Nível: " + nivel, Tabuleiro.LARGURA * CELL_SIZE + 28, 100);
            g2.drawString("Linhas: " + totalLinhas, Tabuleiro.LARGURA * CELL_SIZE + 28, 140);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            g2.setColor(new Color(90,90,160));
            g2.drawString("Próxima peça:", Tabuleiro.LARGURA * CELL_SIZE + 28, 180);

            // Rodapé de controles
            g2.setFont(new Font("Arial", Font.PLAIN, 14));
            g2.setColor(Color.GRAY);
            g2.drawString("S: Salvar | C: Carregar", Tabuleiro.LARGURA * CELL_SIZE + 28, 260);
            g2.drawString("← → para mover, ↑ girar, ↓ acelerar", Tabuleiro.LARGURA * CELL_SIZE + 28, 280);

            // Borda do tabuleiro
            g2.setColor(new Color(110,110,130));
            g2.setStroke(new BasicStroke(6f));
            g2.drawRoundRect(6, 6, Tabuleiro.LARGURA * CELL_SIZE - 2, Tabuleiro.ALTURA * CELL_SIZE - 2, 24, 24);
        }

        private void desenharBloco(Graphics2D g2, int x, int y, Color cor) {
            int px = x*CELL_SIZE, py = y*CELL_SIZE;
            // Gradiente preenchimento
            GradientPaint gp = new GradientPaint(px, py, cor.brighter(), px + CELL_SIZE, py + CELL_SIZE, cor.darker());
            g2.setPaint(gp);
            g2.fillRoundRect(px, py, CELL_SIZE-4, CELL_SIZE-4, 8, 8);
            // Sombra inferior
            g2.setColor(new Color(0,0,0,60));
            g2.fillRoundRect(px+4, py+4, CELL_SIZE-12, CELL_SIZE-12, 8, 8);
            // Borda
            g2.setColor(new Color(30,30,30,190));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(px, py, CELL_SIZE-4, CELL_SIZE-4, 8, 8);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TetrisGUI::new);
    }
}
