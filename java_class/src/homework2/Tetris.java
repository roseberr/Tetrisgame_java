package homework2;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ColorModel;
import java.awt.peer.FramePeer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tetris extends JFrame {
	static Button btn[][] = new Button[20][10];

	int[][][] shapes = { { 
		{ 0, 0, 0, 0 }, 
		{ 0, 1, 1, 0 }, 
		{ 0, 1, 1, 0 }, 
		{ 0, 0, 0, 0 } }, // 'O'

		{ 
		{ 0, 0, 1, 0 }, 
		{ 0, 0, 1, 0 }, 
		{ 0, 0, 1, 0 }, 
		{ 0, 0, 1, 0 } }, // 'I'

		{ 
		{ 0, 0, 0, 0 }, 
		{ 1, 1, 1, 0 }, 
		{ 0, 1, 0, 0 }, 
		{ 0, 0, 0, 0 } }, // 'T'

		{ 
		{ 0, 0, 0, 0 }, 
		{ 0, 0, 1, 1 }, 
		{ 0, 1, 1, 0 }, 
		{ 0, 0, 0, 0 } }, // 'S'

		{ 
		{ 0, 0, 0, 0 },
		{ 0, 1, 1, 0 }, 
		{ 0, 0, 1, 1 },
		{ 0, 0, 0, 0 } }, // 'Z'

		{ 
		{ 0, 0, 1, 0 }, 
		{ 0, 0, 1, 0 }, 
		{ 0, 1, 1, 0 }, 
		{ 0, 0, 0, 0 } }, // 'J'

		{ 
		{ 0, 1, 0, 0 }, 
		{ 0, 1, 0, 0 }, 
		{ 0, 1, 1, 0 }, 
		{ 0, 0, 0, 0 } }// 'L'

	};

	int[][] existpanel = new int[20][10];// �ؿ� �����ϴ� panel
	int[][] newpanel = new int[20][10];// �����̴� panel

	void reset() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				btn[i][j].setBackground(Color.white);

			}
		}
	}

	void reset_newpanel() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				newpanel[i][j] = 0;

			}
		}
	}

	void setting_exist() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (btn[i][j].getBackground() == Color.blue) {
					existpanel[i][j] = 1;
				}

			}
		}

	}

	void draw_existpanel() {// existpanel ������ ��µǰ�

		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (existpanel[i][j] == 1) {
					btn[i][j].setBackground(Color.blue);
				}
			}
		}
	}

	void draw_newpanel() {// existpanel ������ ��µǰ�

		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (newpanel[i][j] == 1) {
					btn[i][j].setBackground(Color.blue);
				}
			}
		}
	}

	void delete_line() {// ���ӵ� ���� ����������� ���� ������ ������ ��ĭ�� ����
		for (int i = 19; i >= 0; i--) {
			boolean linedelete = true;
			for (int j = 0; j < 10; j++) {
				if (existpanel[i][j] != 1) {
					linedelete = false;
				}
			}
			if (linedelete) {
				for (int k = i; k > 0; k--) {
					for (int u = 0; u < 10; u++) {
						existpanel[k][u] = existpanel[k - 1][u];
					}
				}
				i++;
			}

		}
	}

	void endgame() {// ��������� ���
		JOptionPane.showMessageDialog(null, "GAME END");
	}

	boolean file_get() {// java�� run�ҋ� newpanel�� existpanel ���� �ҷ��´�.
		// return ���� ������ִ� ���� ���ο� �� ����� ������ �ϼ��� ������ �װ� Ȯ���ϴ� boolean

		String newpanel_txt = "C:\\newpanel.txt";
		String existpanel_txt = "C:\\existpanel.txt";

		File Fnewpanel = new File(newpanel_txt);
		File Fexistpanel = new File(existpanel_txt);

		boolean make_newpanel = true;

		try (

				FileReader FnpReader = new FileReader(Fnewpanel);
				FileReader FepReader = new FileReader(Fexistpanel);

		// FileWriter fw=new FileWriter(input);
		// BufferedWriter bw=new BufferedWriter(fw);

		// FileInputStream fis= new FileInputStream(input);
		// FileOutputStream fos=new FileOutputStream(output)
		) {

			// bw.write("1111");
			// bw.newLine();
			// bw.flush();
			// bw.close();
			// fw.close();

			int c = 0;
			int i = 0;
			int j = 0;

			while ((c = FnpReader.read()) != -1) {
				// System.out.print((char)c);
				if (c == '0' | c == '1') {
					newpanel[i][j] = c - '0';
					j++;
					if (j == 10) {
						i++;
						j = 0;
					}
					if (c == '1') {
						make_newpanel = false;
					}
				}
			}
			// System.out.println(make_newpanel);

			i = 0;
			j = 0;
			while ((c = FepReader.read()) != -1) {
				// System.out.print((char)c);
				if (c == '1' | c == '0') {
					// System.out.print((char)c);
					existpanel[i][j] = c - '0';
					// System.out.println(existpanel[i][j]);
					j++;
					if (j == 10) {
						i++;
						j = 0;
					}
				}
			}

		} catch (IOException e) {
		}
		return make_newpanel;

	}

	void file_out() {// ���� ���� existpanel newpanel�� ���� ���Ϸ� ���� thread�� ���ؼ� ���������� ���ư�

		String newpanel_txt = "C:\\newpanel.txt";
		String existpanel_txt = "C:\\existpanel.txt";

		File Fnewpanel = new File(newpanel_txt);
		File Fexistpanel = new File(existpanel_txt);

		try (

				// FileReader FnpReader = new FileReader(Fnewpanel);
				// FileReader FepReader = new FileReader(Fexistpanel);

				FileWriter fw = new FileWriter(newpanel_txt); // newpanel ����
				BufferedWriter bw = new BufferedWriter(fw);) {

			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 10; j++) {
					if (newpanel[i][j] == 0) {
						bw.write('0');
					} else if (newpanel[i][j] == 1) {
						bw.write('1');
					}
				}
				bw.newLine();
			}
			// bw.write("1111");
			// bw.newLine();

			bw.flush();
			bw.close();
			fw.close();

			FileWriter fw_exist = new FileWriter(existpanel_txt);// exist panel ����
			BufferedWriter bw_exist = new BufferedWriter(fw_exist);		

			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 10; j++) {
					if (existpanel[i][j] == 0) {
						// System.out.print(0);
						bw_exist.write('0');
					} else if (existpanel[i][j] == 1) {
						bw_exist.write('1');
						// System.out.print(1);
					} 
				}
				// System.out.println();
				bw_exist.newLine();
			}
			// System.out.print("error count::"+count);
			// bw.write("1111");
			// bw.newLine();
			bw_exist.flush();
			bw_exist.close();
			fw_exist.close();

			// draw_existpanel();
			// draw_newpanel();
		} catch (IOException e) {
		}

	}

	void file_clear() {// gameover�ϋ� existpanel,newpanel �ʱ�ȭ �׷����� gameover�ϋ� �ٽ� ó������ �����Ҽ�����

		String newpanel_txt = "C:\\newpanel.txt";
		String existpanel_txt = "C:\\existpanel.txt";

		File Fnewpanel = new File(newpanel_txt);
		File Fexistpanel = new File(existpanel_txt);

		try (

				FileWriter fw = new FileWriter(newpanel_txt); // newpanel ����
				BufferedWriter bw = new BufferedWriter(fw);) {

			bw.flush();
			bw.close();
			fw.close();

			FileWriter fw_exist = new FileWriter(existpanel_txt);// exist panel ����
			BufferedWriter bw_exist = new BufferedWriter(fw_exist);

			bw_exist.flush();
			bw_exist.close();
			fw_exist.close();

		} catch (IOException e) {
		}

	}

	class moveTetris implements Runnable {
		boolean make_new_tetris = true;
		int[][] nextShape = new int[4][4];

		@Override
		public void run() {

			make_new_tetris = file_get(); //

			while (true) {

				// file_get();
				file_out();

				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
				}

				reset();

				if (make_new_tetris) {// tetris ���� �����
					
					int val = (int) (Math.random() * 7);
					nextShape = shapes[val];

					make_new_tetris = false;
					reset_newpanel();

					boolean gameover = false;

					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							if (existpanel[i][j] == 1 && nextShape[i][j] == 1) {
								gameover = true;
							}

						}
					}
					if (gameover) {
						endgame();
						gameover = false;

						file_clear();
						System.exit(0);

					}

					for (int i = 0; i < 4; i++) {
						for (int j = 0; j < 4; j++) {
							if (nextShape[i][j] == 1) {

								newpanel[i][j] = 1;

							}
						}
					}
				}

				draw_existpanel();
				draw_newpanel();// newpanel �׸���

				Loop1: for (int i = 19; i >= 0; i--) {
					for (int j = 9; j >= 0; j--) {
						if (newpanel[i][j] == 1) {

							if (i == 19 || (i < 19 & existpanel[i + 1][j] == 1)) {// newpanel�� �ǾƷ� �����ϰų� �ؿ� ��ư�� �����ҋ�
								make_new_tetris = true;

								setting_exist();// ���� exist�� newpanel�̶� ��ġ��
								reset_newpanel();
								draw_existpanel();
								delete_line();
								draw_existpanel();

								break Loop1;
							} else {
								newpanel[i][j] = 0;
								newpanel[i + 1][j] = 1;
							}
						}
					}
				}

				draw_existpanel();

			}

		}
	}

	public Tetris() {
		JPanel p1 = new JPanel(new GridLayout(20, 10));

		Thread t = new Thread(new moveTetris());
		// file_get();
		t.start();

		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				btn[i][j] = new Button();
				Button b = btn[i][j];
				b.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						// b.setBackground(Color.blue);

					}

				});

				b.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub

					}

					public void keyPressed(KeyEvent e) {

						if (e.getKeyCode() == 32) {// rotate�Լ�
							// System.out.print("rotate\n");
							int minx = 99;
							int miny = 99;
							int[][] savenewpanel = new int[4][4];
							for (int i = 0; i < 20; i++) {
								for (int j = 0; j < 10; j++) {
									if (newpanel[i][j] == 1) {
										if (j < minx) {
											minx = j;
										}
										if (i < miny) {
											miny = i;
										}

									}

								}
							}

							for (int i = 0; i < 4; i++) {// newpanel�� rotate�ҋ� �ٽ� ���� ��ġ�� �����ϱ����ؼ�
								for (int j = 0; j < 4; j++) {
									if (i + miny < 20 && j + minx < 10) {

										savenewpanel[i][j] = newpanel[i + miny][j + minx];

									}
								}
							}

							int[][] rotatepanel = new int[4][4];

							for (int i = 0; i < 4; i++) {// rotate�κ�

								for (int j = 0; j < 4; j++) {

									rotatepanel[i][j] = savenewpanel[3 - j][i];

								}

							}
							int rminx = 99;
							int rminy = 99;

							for (int i = 0; i < 4; i++) {// rotatepanel�� ��minx miny�� �Ǻ�
								for (int j = 0; j < 4; j++) {
									savenewpanel[i][j] = 0;
									if (rotatepanel[i][j] == 1) {
										if (j < rminx) {
											rminx = j;
										}
										if (i < rminy) {
											rminy = i;
										}
									}
								}
							}

							// System.out.println(rminx);
							for (int i = 0; i < 4; i++) {// rotatepanel�� ���������� ���°� ����
								for (int j = rminx; j < 4; j++) {
									savenewpanel[i][j - rminx] = rotatepanel[i][j];
								}
							}

							int rmaxx = 0;// savenewpanel�� �ִ� x,y�����˱����ؼ� �ֳ�? �׷��� ������Ʈ�ҋ� �������ּ� �������� �ʰ��ϱ����ؼ�
							int rmaxy = 0;

							for (int i = 0; i < 4; i++) {
								for (int j = 0; j < 4; j++) {
									if (savenewpanel[i][j] == 1) {
										if (i > rmaxy) {
											rmaxy = i;
										}
										if (j > rmaxx) {
											rmaxx = j;
										}

									}

								}
							}

							if (rmaxy + miny < 20 && rmaxx + minx < 10) {
								for (int i = 0; i < 4; i++) {
									for (int j = 0; j < 4; j++) {
										if (i + miny < 20 && j + minx < 10) {

											newpanel[i + miny][j + minx] = savenewpanel[i][j];

										}
									}
								}
							}

							draw_existpanel();
							// draw_newpanel();

						}

						if (e.getKeyCode() == 39) {// �������̵�
							reset();
							boolean lp = true;
							draw_existpanel();
							for (int i = 0; i < 20; i++) {// ������ �ű拚 �ǿ��������� �ƴ��� Ȯ���Ѵ�.
								if (newpanel[i][9] == 1) {
									lp = false;
									break;

								}
							}

							for (int i = 0; i < 20; i++) {// ������ �ű拚 �浹�ϳ� ���ϳ� Ȯ��
								for (int j = 0; j < 9; j++) {
									if (newpanel[i][j] == 1 && existpanel[i][j + 1] == 1) {
										lp = false;
										break;

									}
								}
							}

							if (lp) {// lp ������ ������� �ʰ� �ϱ����ؼ� �����Ѵ� lp==false�̸� ������ ��������
								for (int i = 0; i < 20; i++) {

									for (int j = 8; j >= 0; j--) {
										if (newpanel[i][j] == 1) {
											newpanel[i][j] = 0;
											newpanel[i][j + 1] = 1;
											btn[i][j].setBackground(Color.white);
											btn[i][j + 1].setBackground(Color.blue);
										}
									}

								}

							} else {// lp�� false�ϰ�� �������� �ʰ� �״�� ���
								draw_newpanel();

							}
						}

						else if (e.getKeyCode() == 37) {// �����̵�
							reset();
							boolean lp = true;

							draw_existpanel();
							for (int i = 0; i < 20; i++) {
								if (newpanel[i][0] == 1) {// ���� �ǳ����� ������ ������� �ʰ��ϱ����ؼ�
									lp = false;
									break;
								}
							}

							for (int i = 0; i < 20; i++) {// ���ʿ� �̹� ���� �����ϸ� ��������
								for (int j = 1; j < 10; j++) {
									if (newpanel[i][j] == 1 && existpanel[i][j - 1] == 1) {
										lp = false;
										break;

									}
								}
							}

							if (lp) {
								for (int i = 0; i < 20; i++) {

									for (int j = 1; j < 10; j++) {

										if (newpanel[i][j] == 1) {
											newpanel[i][j] = 0;
											newpanel[i][j - 1] = 1;
											btn[i][j].setBackground(Color.white);
											btn[i][j - 1].setBackground(Color.blue);
										}
									}

								}

							} else {
								draw_newpanel();

							}
						}

						if (e.getKeyCode() == 40) {// ������ ���� ��

							boolean lp = true;
							reset();

							for (int i = 0; i < 19; i++) {// newpanel �ؿ� existpanel�� ������� �������̰�
								for (int j = 0; j < 10; j++) {
									if (existpanel[i + 1][j] == 1 && newpanel[i][j] == 1) {
										lp = false;
									}
								}
							}

							for (int j = 0; j < 10; j++) {
								if (newpanel[19][j] == 1) {
									lp = false;
								}
							}

							if (lp) {
								for (int i = 19; i > 0; i--) {
									for (int j = 0; j < 10; j++) {

										newpanel[i][j] = newpanel[i - 1][j];

									}
								}
							}

							draw_newpanel();
							draw_existpanel();

						}

					}

				});

				p1.add(btn[i][j]);

			}

		}

		add(p1);
		setVisible(true);
		setResizable(false);
		setSize(400, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		new Tetris();
	}

}
