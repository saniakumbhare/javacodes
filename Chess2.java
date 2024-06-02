

package chess;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

import chess.ReturnPiece.PieceFile;
import chess.ReturnPiece.PieceType;
import chess.Chess.Player;

class ReturnPiece {
	static enum PieceType {WP, WR, WN, WB, WQ, WK, 
		            BP, BR, BN, BB, BK, BQ};
	static enum PieceFile {a, b, c, d, e, f, g, h};
	
	PieceType pieceType;
	PieceFile pieceFile;
	int pieceRank;  

	
	public String toString() {
		return ""+pieceFile+pieceRank+":"+pieceType;
	}
	public boolean equals(Object other) {
		if (other == null || !(other instanceof ReturnPiece)) {
			return false;
		}
		ReturnPiece otherPiece = (ReturnPiece)other;
		return pieceType == otherPiece.pieceType &&
				pieceFile == otherPiece.pieceFile &&
				pieceRank == otherPiece.pieceRank;
	}
}
class Storage {
    
	static ArrayList<ReturnPiece> chessPiecesAL = new ArrayList<>();
    static ReturnPiece[][] storageBoard = new ReturnPiece[8][8]; 
	static Player currPlayer = Player.white;
	public static HashMap<Character, PieceFile> fileMap = new HashMap<>();
	public static HashMap<Integer, PieceFile> fileMap2 = new HashMap<>();
    static {
        fileMap.put('a', PieceFile.a); 
        fileMap.put('b', PieceFile.b);
        fileMap.put('c', PieceFile.c);
        fileMap.put('d', PieceFile.d);
        fileMap.put('e', PieceFile.e);
        fileMap.put('f', PieceFile.f);
        fileMap.put('g', PieceFile.g);
        fileMap.put('h', PieceFile.h);
		fileMap2.put(0, PieceFile.a); 
        fileMap2.put(1, PieceFile.b);
        fileMap2.put(2, PieceFile.c);
        fileMap2.put(3, PieceFile.d);
        fileMap2.put(4, PieceFile.e);
        fileMap2.put(5, PieceFile.f);
        fileMap2.put(6, PieceFile.g);
        fileMap2.put(7, PieceFile.h);
    }
		
	public static void switchPlayer() {
		if(currPlayer == Player.black) {
			currPlayer = Player.white;
		} else {
			currPlayer = Player.black;
		}
	}

	static ArrayList<PieceType> whites = new ArrayList<>();
	static {
		whites.add(PieceType.WP);
        whites.add(PieceType.WR);
        whites.add(PieceType.WN);
        whites.add(PieceType.WB);
        whites.add(PieceType.WQ);
        whites.add(PieceType.WK);
	}
	
	public static boolean isWhite(ReturnPiece rp){
		return whites.contains(rp.pieceType);
	}
	static PieceFile whitefile = PieceFile.d;
	static int whiterank = 1;
	static PieceFile blackfile = PieceFile.e;
	static int blackrank = 8;
	static PieceFile attackFile;
	static int attackRank;

	public static boolean isChecked() {
		boolean result = false;
		for (int i = 0; i < storageBoard.length; i++) {
			for (int j = 0; j < storageBoard.length; j++) {
				if(storageBoard[i][j] != null) {
					ReturnPiece RP = storageBoard[i][j];
					if(RP.pieceType.ordinal() == PieceType.WK.ordinal()) {
						whitefile = fileMap2.get(j);
						whiterank = 1 + i;
					}
					if(RP.pieceType == PieceType.BK) {
						blackfile = fileMap2.get(j);
						blackrank = 1 + i;
					}
						
				}
			}
		}
		
		for (int i = 0; i < storageBoard.length; i++) {
			for (int j = 0; j < storageBoard.length; j++) {
				if(storageBoard[i][j] != null) {
					ChessPiece CP = (ChessPiece) storageBoard[i][j];
					if(currPlayer == Player.white) {
						if(CP.isValid(whitefile, whiterank) && !(isWhite(CP))) { 
							attackFile = CP.pieceFile;
							attackRank = CP.pieceRank;
							result = true;
						}
					} else {
						if(CP.isValid(blackfile, blackrank) && (isWhite(CP))) { 
							attackFile = CP.pieceFile;
							attackRank = CP.pieceRank;
							result = true;
						}
					}
				}
			}
		}
		return result;
	}

	public static boolean CheckM8() {
		boolean checkmated = false; 
		if(isChecked()) {
			checkmated = true;
			if(currPlayer == Player.white) {
				
				
				ChessPiece king = (ChessPiece)storageBoard[whiterank - 1][whitefile.ordinal()];
				int[] dr = {1, -1, 0, 0, 1, -1, 1, -1};
				int[] dc = {0, 0, -1, 1, 1, -1, -1, 1};

				for (int k = 0; k < 8; k++) {
					int newRow = blackrank + dr[k];
					int newCol = blackfile.ordinal() + dc[k];
					
					
					if (newRow >= 0 && newRow < storageBoard.length && newCol >= 0 && newCol < storageBoard[0].length) {
						if (storageBoard[newRow][newCol] == null) {
							
							if (king.isValid(fileMap2.get(newCol), newRow + 1)) {
								checkmated = false;
							}
							if(checkmated == false) {
								break;
							}
						}
					}
				}
			 
				
				ChessPiece attacker = (ChessPiece)storageBoard[attackRank-1][attackFile.ordinal()];
				ArrayList<int[]> attackMoves = new ArrayList<>();
				int horizontal = whiterank - attackRank;
				int vertical = whitefile.ordinal() - attackFile.ordinal();
				if(!(Storage.isWhite(attacker))) {
					
					
					
					if(attacker.pieceType == PieceType.BR || attacker.pieceType == PieceType.BQ) {
						if(horizontal == 0 && vertical != 0){	
							if(vertical > 0) {
								for (int i = 1; i < vertical; i++) {
									int[] arr = new int[2];
									arr[0] = attackRank-1 + i;
									arr[1] = attackFile.ordinal();
									attackMoves.add(arr);
								}
							} else {
								for (int i = 1; i < Math.abs(vertical); i++) {
									int[] arr = new int[2];
									arr[0] = attackRank-1 - i;
									arr[1] = attackFile.ordinal();
									attackMoves.add(arr);
								}
							}
						}
						if(horizontal != 0 && vertical == 0){	
							if(horizontal > 0) {
								for (int i = 1; i < horizontal; i++) {
									int[] arr = new int[2];
									arr[0] = attackRank-1;
									arr[1] = attackFile.ordinal() + i;
									attackMoves.add(arr);
								}
							} else {
								for (int i = 1; i < Math.abs(horizontal); i++) {
									int[] arr = new int[2];
									arr[0] = attackRank-1;
									arr[1] = attackFile.ordinal() - i;
									attackMoves.add(arr);
								}
							}
						}
					}
					
					if (attacker.pieceType == PieceType.BB || attacker.pieceType == PieceType.BQ) {

						int diagonalDistance = Math.abs(attackRank - whiterank);
						for (int i = 1; i < diagonalDistance; i++) {
							
							if (attackRank < whiterank && attackFile.ordinal() < whitefile.ordinal()) {
								int[] arr = {attackRank - 1 + i, attackFile.ordinal() + i};
								attackMoves.add(arr);
							}
							
							else if (attackRank < whiterank && attackFile.ordinal() > whitefile.ordinal()) {
								int[] arr = {attackRank - 1 + i, attackFile.ordinal() - i};
								attackMoves.add(arr);
							}
							
							else if (attackRank > whiterank && attackFile.ordinal() < whitefile.ordinal()) {
								int[] arr = {attackRank - 1 - i, attackFile.ordinal() + i};
								attackMoves.add(arr);
							}
							
							else if (attackRank > whiterank && attackFile.ordinal() > whitefile.ordinal()) {
								int[] arr = {attackRank - 1 - i, attackFile.ordinal() - i};
								attackMoves.add(arr);
							}
						}
					}
					
					
					
				} 
				for (ReturnPiece[] row : Storage.storageBoard) {
					for (ReturnPiece returnPiece : row) {
						if(returnPiece != null) {
							ChessPiece CP = (ChessPiece)returnPiece;
							if(CP.isValid(attackFile, attackRank) && (isWhite(returnPiece))) { 
								checkmated = false;
							}
							if(checkmated == false) {
								break;
							}
							if(CP.pieceType != PieceType.WK && (isWhite(CP))) {
								for (int i = 0; i < attackMoves.size(); i++) {
									int[] arr = attackMoves.get(i);
									if(CP.isValid(fileMap2.get(arr[1]), arr[0] + 1)) {
										checkmated = false;
									}
									if(checkmated == false) {
										break;
									}
								}
							}
						}
					}
				}
				return checkmated;
				


			} else {

				
				ChessPiece king = (ChessPiece)storageBoard[blackrank - 1][blackfile.ordinal()];
				
				int[] dr = {1, -1, 0, 0, 1, -1, 1, -1};
				int[] dc = {0, 0, -1, 1, 1, -1, -1, 1};

				for (int k = 0; k < 8; k++) {
					int newRow = blackrank - 1 + dr[k];
					int newCol = blackfile.ordinal() + dc[k];
					
					
					if (newRow >= 0 && newRow < storageBoard.length && newCol >= 0 && newCol < storageBoard[0].length) {
						if (storageBoard[newRow][newCol] == null) {
							
							if (king.isValid(fileMap2.get(newCol), newRow + 1)) { 
								checkmated = false;
							}
							if(checkmated == false) {
								break;
							}
						}
					}
				}
			

				
				ChessPiece attacker = (ChessPiece)storageBoard[attackRank-1][attackFile.ordinal()];
				ArrayList<int[]> attackMoves = new ArrayList<>();
				int horizontal = blackrank - attackRank;
				int vertical = blackfile.ordinal() - attackFile.ordinal();
				if(Storage.isWhite(attacker)) {
					
					if(attacker.pieceType == PieceType.WR || attacker.pieceType == PieceType.WQ) {
						if(horizontal == 0 && vertical != 0){	
							if(vertical > 0) {
								for (int i = 1; i < vertical; i++) {
									int[] arr = new int[2];
									arr[0] = attackRank-1 + i;
									arr[1] = attackFile.ordinal();
									attackMoves.add(arr);
								}
							} else {
								for (int i = 1; i < Math.abs(vertical); i++) {
									int[] arr = new int[2];
									arr[0] = attackRank-1 - i;
									arr[1] = attackFile.ordinal();
									attackMoves.add(arr);
								}
							}
						}
						if(horizontal != 0 && vertical == 0){	
							if(horizontal > 0) {
								for (int i = 1; i < horizontal; i++) {
									int[] arr = new int[2];
									arr[0] = attackRank-1;
									arr[1] = attackFile.ordinal() + i;
									attackMoves.add(arr);
								}
							} else {
								for (int i = 1; i < Math.abs(horizontal); i++) {
									int[] arr = new int[2];
									arr[0] = attackRank-1;
									arr[1] = attackFile.ordinal() - i;
									attackMoves.add(arr);
								}
							}
						}
					}
					
					if (attacker.pieceType == PieceType.WB || attacker.pieceType == PieceType.WQ) {

						int diagonalDistance = Math.abs(attackRank - blackrank);
						for (int i = 1; i < diagonalDistance; i++) {
							
							if (attackRank < whiterank && attackFile.ordinal() < blackfile.ordinal()) {
								int[] arr = {attackRank - 1 + i, attackFile.ordinal() + i};
								attackMoves.add(arr);
							}
							
							else if (attackRank < whiterank && attackFile.ordinal() > blackfile.ordinal()) {
								int[] arr = {attackRank - 1 + i, attackFile.ordinal() - i};
								attackMoves.add(arr);
							}
							
							else if (attackRank > whiterank && attackFile.ordinal() < blackfile.ordinal()) {
								int[] arr = {attackRank - 1 - i, attackFile.ordinal() + i};
								attackMoves.add(arr);
							}
							
							else if (attackRank > whiterank && attackFile.ordinal() > blackfile.ordinal()) {
								int[] arr = {attackRank - 1 - i, attackFile.ordinal() - i};
								attackMoves.add(arr);
							}
						}
					}

				}
				
				
				for (ReturnPiece[] row : Storage.storageBoard) {
					for (ReturnPiece returnPiece : row) {
						if(returnPiece != null) {
							ChessPiece CP = (ChessPiece)returnPiece;
							if(CP.isValid(attackFile, attackRank) && !(isWhite(returnPiece))) { 
								checkmated = false;
							}
							if(checkmated == false) {
								break;
							}
							if(CP.pieceType != PieceType.BK && !(isWhite(CP))) {
								for (int i = 0; i < attackMoves.size(); i++) {
									int[] arr = attackMoves.get(i);
									if(CP.isValid(fileMap2.get(arr[1]), arr[0]+1)) {
										checkmated = false;
									}
									if(checkmated == false) {
										break;
									}
								}
							}
						}
					}
				}
				return checkmated;
			}
		}
		return checkmated;
	}
	public static boolean quickSimulate(PieceFile startFile, int startRank, PieceFile endFile, int endRank) {
		if(!Storage.isChecked()) {
			return true;
		}
		ChessPiece p = (ChessPiece) Storage.storageBoard[startRank-1][startFile.ordinal()];
		int ogTimesMoved = p.timesMoved;
		if(p.isValid(endFile, endRank)){
			if(storageBoard[endRank-1][endFile.ordinal()] != null) {
				if(storageBoard[endRank-1][endFile.ordinal()] == storageBoard[attackRank-1][attackFile.ordinal()]) {
					return true;
				}
			} else {
				p.moveTo(endFile, endRank);
				if(!isChecked()) {
					p.moveTo(startFile, startRank);
					p.timesMoved = ogTimesMoved;
					return true;
				}
				p.moveTo(startFile, startRank);
				p.timesMoved = ogTimesMoved;
			}
		}
		return false;
	}

	public static boolean selfCheck(PieceFile startFile, int startRank, PieceFile endFile, int endRank) {
		ChessPiece p = (ChessPiece) Storage.storageBoard[startRank-1][startFile.ordinal()];
		int ogTimesMoved = p.timesMoved;
		if(p.isValid(endFile, endRank)){
				p.moveTo(endFile, endRank);
				if(!isChecked()) {
					p.moveTo(startFile, startRank);
					p.timesMoved = ogTimesMoved;
					return false;
				} else { 
					p.moveTo(startFile, startRank);
					p.timesMoved = ogTimesMoved;
					return true;
				}
		}
		return true;
	}
	

	public static void pawnPromotion(ReturnPiece piece, char promotionChar) {

		
		if ((piece instanceof Pawn) && ((piece.pieceRank == 1 && piece.pieceType == PieceType.BP) ||
										(piece.pieceRank == 8 && piece.pieceType == PieceType.WP))) {
			
			int index = chessPiecesAL.indexOf(piece);
			ChessPiece hehe = (ChessPiece)piece; 
			int placeRank = piece.pieceRank;
			PieceFile placeFile = piece.pieceFile;
			int iLikeToMoveItMoveIt = hehe.timesMoved;
			boolean rights = isWhite(hehe);
			switch (promotionChar) {
				case 'Q':
					piece = new Queen(placeFile, placeRank, rights, iLikeToMoveItMoveIt);
					break;
				case 'R':
					piece = new Rook(placeFile, placeRank, rights, iLikeToMoveItMoveIt);
					break;
				case 'B':
					piece = new Bishop(placeFile, placeRank, rights, iLikeToMoveItMoveIt);
					break;
				case 'N':
					piece = new Knight(placeFile, placeRank, rights, iLikeToMoveItMoveIt);
					break;
				default:
					piece = new Queen(placeFile, placeRank, rights, iLikeToMoveItMoveIt);
					break;
			}
			chessPiecesAL.set(index, piece);
			storageBoard[placeRank - 1][placeFile.ordinal()] = piece;
		}
	}
	
	}


class ChessPiece extends ReturnPiece {
	public int timesMoved;
	public void moveTo(PieceFile file, int rank) {
        Storage.storageBoard[pieceRank-1][pieceFile.ordinal()] = null;
        if(!(Storage.storageBoard[rank-1][file.ordinal()] == null)) {
			
            ChessPiece killed = (ChessPiece)Storage.storageBoard[rank-1][file.ordinal()];    
			Storage.chessPiecesAL.remove(Storage.storageBoard[rank-1][file.ordinal()]);     
			
			System.out.println("CHESSPIECE: " + killed +  " WAS taken by: " + this);
            }
        Storage.storageBoard[rank-1][file.ordinal()] = this;
        this.pieceFile = file;
		this.pieceRank = rank;
		
    }
    public boolean isValid(PieceFile file, int rank) {
		return true; 
	}
}

class Pawn extends ChessPiece {
	public Pawn() {
		this.pieceType = PieceType.WP;
        this.pieceFile = PieceFile.a;
		this.pieceRank = 0;
        this.timesMoved = 0;
    }
    public Pawn(PieceFile file, int rank, boolean isWhite, int timesMoved) {

        if(isWhite) {
			this.pieceType = PieceType.WP;
		} else {this.pieceType = PieceType.BP;}
        this.pieceFile = file;
		this.pieceRank = rank;
        this.timesMoved = timesMoved;
    }
    public boolean isValid(PieceFile file, int rank) {
        
        if(this.pieceType == PieceType.WP) {
            int vertical = rank - this.pieceRank; 
            int horizontal = file.ordinal()-this.pieceFile.ordinal(); 
            if (vertical == 2 && horizontal == 0 && this.timesMoved == 0) {
				
				if (Storage.storageBoard[this.pieceRank][this.pieceFile.ordinal()] == null &&
					Storage.storageBoard[this.pieceRank + 1][this.pieceFile.ordinal()] == null) {
					return true;
				}
				return false;
			} else if (vertical == 1 && horizontal == 0) {
				
				if (Storage.storageBoard[rank - 1][file.ordinal()] == null) {
					return true;
				}
				return false;
			} else if (vertical == 1 && Math.abs(horizontal) == 1) {
				
				if (Storage.storageBoard[rank - 1][file.ordinal()] != null) {
					return true;
				}
				return false;
			}
			return false;
        } else if (this.pieceType == PieceType.BP){
            int vertical = rank - this.pieceRank; 
            int horizontal = file.ordinal()-this.pieceFile.ordinal();
            if (vertical == -2 && horizontal == 0 && this.timesMoved == 0) {
				
				if (Storage.storageBoard[this.pieceRank - 2][this.pieceFile.ordinal()] == null &&
					Storage.storageBoard[this.pieceRank - 3][this.pieceFile.ordinal()] == null) {
					return true;
				}
				return false;
			} else if (vertical == -1 && horizontal == 0) {
				
				if (Storage.storageBoard[rank - 1][file.ordinal()] == null) {
					return true;
				}
				return false;
			} else if (vertical == -1 && Math.abs(horizontal) == 1) {
				
				if (Storage.storageBoard[rank - 1][file.ordinal()] != null) {
					return true;
				}
				return false;
			}
			return false;
        }
		return false;
	}
	public void moveTo(PieceFile file, int rank) {
        super.moveTo(file, rank); 
        this.timesMoved++;
		
    }
}

class Bishop extends ChessPiece {

	public Bishop() {
		this.pieceType = PieceType.WR;
        this.pieceFile = PieceFile.a;
		this.pieceRank = 0;
        this.timesMoved = 0;
    }

    public Bishop(PieceFile file, int rank, boolean isWhite, int timesMoved) {
        super(); 
        this.pieceFile = file;
        this.pieceRank = rank;
        this.pieceType = isWhite ? PieceType.WB : PieceType.BB;
		this.timesMoved = timesMoved; 
    }

    public boolean isValid(PieceFile file, int rank) {
    	int vertical = rank - this.pieceRank;
    	int horizontal = file.ordinal() - this.pieceFile.ordinal();

   
		
		if (Math.abs(horizontal) == Math.abs(vertical)) {

			if (horizontal > 0 && vertical > 0) {
				
				for (int i = 1; i < horizontal; i++) {
					if (Storage.storageBoard[this.pieceRank - 1 + i][this.pieceFile.ordinal() + i] != null) {
						return false;
					}
				}

			} else if (horizontal < 0 && vertical > 0) {
				
				for (int i = 1; i < Math.abs(horizontal); i++) {
					if (Storage.storageBoard[this.pieceRank - 1 + i][this.pieceFile.ordinal() - i] != null) {
						return false;
					}
				}

			} else if (horizontal > 0 && vertical < 0) {
				
				for (int i = 1; i < horizontal; i++) {
					if (Storage.storageBoard[this.pieceRank - 1 - i][this.pieceFile.ordinal() + i] != null) {
						return false;
					}
				}

			} else if (horizontal < 0 && vertical < 0) {
				
				for (int i = 1; i < Math.abs(horizontal); i++) {
					if (Storage.storageBoard[this.pieceRank - 1 - i][this.pieceFile.ordinal() - i] != null) {
						return false;
					}
				}
				
			}
			return true; 
		}
		return false; 
	}
	
	public void moveTo(PieceFile file, int rank) {
        super.moveTo(file, rank); 
        this.timesMoved++;
    }

    }


class Queen extends ChessPiece {

	public Queen() {
		this.pieceType = PieceType.WR;
        this.pieceFile = PieceFile.a;
		this.pieceRank = 0;
        this.timesMoved = 0;
    }

    public Queen(PieceFile file, int rank, boolean isWhite, int timesMoved) {
        super(); 
        this.pieceFile = file;
        this.pieceRank = rank;
        this.pieceType = isWhite ? PieceType.WQ : PieceType.BQ;
        this.timesMoved = timesMoved; 
    }

    public boolean isValid(PieceFile file, int rank) {
        int verticalMove = Math.abs(rank - this.pieceRank);
        int horizontalMove = Math.abs(file.ordinal() - this.pieceFile.ordinal());
        
        boolean isDiagonalMove = verticalMove == horizontalMove;
        boolean isStraightMove = verticalMove == 0 || horizontalMove == 0;

		
		int vertical = rank - this.pieceRank; 
        int horizontal = file.ordinal()-this.pieceFile.ordinal(); 
		if(isDiagonalMove){
			
			if (horizontal > 0 && vertical > 0) {
				
				for (int i = 1; i < horizontal; i++) {
					if (Storage.storageBoard[this.pieceRank - 1 + i][this.pieceFile.ordinal() + i] != null) {
						return false;
					}
				}

			} else if (horizontal < 0 && vertical > 0) {
				
				for (int i = 1; i < Math.abs(horizontal); i++) {
					if (Storage.storageBoard[this.pieceRank - 1 + i][this.pieceFile.ordinal() - i] != null) {
						return false;
					}
				}

			} else if (horizontal > 0 && vertical < 0) {
				
				for (int i = 1; i < horizontal; i++) {
					if (Storage.storageBoard[this.pieceRank - 1 - i][this.pieceFile.ordinal() + i] != null) {
						return false;
					}
				}

			} else if (horizontal < 0 && vertical < 0) {
				
				for (int i = 1; i < Math.abs(horizontal); i++) {
					if (Storage.storageBoard[this.pieceRank - 1 - i][this.pieceFile.ordinal() - i] != null) {
						return false;
					}
				}
				
			}
			return true; 
		}
		if(isStraightMove){
			
			if(horizontal == 0 && vertical != 0){
				if(vertical > 0) {
					for (int i = 1; i < vertical; i++) {
						if(!(Storage.storageBoard[this.pieceRank-1 + i][this.pieceFile.ordinal()] == null)) {
							return false;
						}
					}
				} else {
					for (int i = 1; i < Math.abs(vertical); i++) {
						if(!(Storage.storageBoard[this.pieceRank-1 - i][this.pieceFile.ordinal()] == null)) {
							return false;						}
					}
				}
				return true;
			}
			if(vertical == 0 && horizontal != 0){
				if(horizontal > 0) {
					for (int i = 1; i < horizontal; i++) {
						if(!(Storage.storageBoard[this.pieceRank - 1][this.pieceFile.ordinal()  + i] == null)) {
							return false;
						}
					}
				} else {
					for (int i = 1; i < Math.abs(horizontal); i++) {
						if(!(Storage.storageBoard[this.pieceRank - 1][this.pieceFile.ordinal()  - i] == null)) {
							return false;
						}
					}
				}
				return true;
			}
		}
		return false;
    }

    public void moveTo(PieceFile file, int rank) {
        super.moveTo(file, rank); 
        this.timesMoved++;
    }
}


class Rook extends ChessPiece {
	public Rook() {
		this.pieceType = PieceType.WR;
        this.pieceFile = PieceFile.a;
		this.pieceRank = 0;
        this.timesMoved = 0;
    }
    public Rook(PieceFile file, int rank, boolean isWhite, int timesMoved) {
        if(isWhite) {
			this.pieceType = PieceType.WR;
		} else {this.pieceType = PieceType.BR;}
        this.pieceFile = file;
		this.pieceRank = rank;
        this.timesMoved = timesMoved;
    }
	public boolean isValid(PieceFile file, int rank) {
		int vertical = rank - this.pieceRank; 
        int horizontal = file.ordinal()-this.pieceFile.ordinal(); 
		if(horizontal == 0 && vertical != 0){
			if(vertical > 0) {
				for (int i = 1; i < vertical; i++) {
					if(!(Storage.storageBoard[this.pieceRank-1 + i][this.pieceFile.ordinal()] == null)) {
						return false;
					}
				}
			} else {
				for (int i = 1; i < Math.abs(vertical); i++) {
					if(!(Storage.storageBoard[this.pieceRank-1 - i][this.pieceFile.ordinal()] == null)) {
						return false;
					}
				}
			}
			return true;
		}
		if(vertical == 0 && horizontal != 0){
			if(horizontal > 0) {
				for (int i = 1; i < horizontal; i++) {
					if(!(Storage.storageBoard[this.pieceRank - 1][this.pieceFile.ordinal()  + i] == null)) {
						return false;
					}
				}
			} else {
				for (int i = 1; i < Math.abs(horizontal); i++) {
					if(!(Storage.storageBoard[this.pieceRank - 1][this.pieceFile.ordinal()  - i] == null)) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	public void moveTo(PieceFile file, int rank) {
        super.moveTo(file, rank); 
        this.timesMoved++;
    }
}

class Knight extends ChessPiece {
	public Knight() {
		this.pieceType = PieceType.WN;
        this.pieceFile = PieceFile.b;
		this.pieceRank = 0;
        this.timesMoved = 0;
    }
    public Knight(PieceFile file, int rank, boolean isWhite, int timesMoved) {
        if(isWhite) {
			this.pieceType = PieceType.WN;
		} else {this.pieceType = PieceType.BN;}
        this.pieceFile = file;
		this.pieceRank = rank;
        this.timesMoved = timesMoved;
    }
	public boolean isValid(PieceFile file, int rank) {
		int vertical = rank - this.pieceRank; 
        int horizontal = file.ordinal()-this.pieceFile.ordinal(); 
		if ((Math.abs(vertical) == 2 && Math.abs(horizontal) == 1) ||
        (Math.abs(horizontal) == 2 && Math.abs(vertical) == 1)) {
        return true;
    	}
		return false;
	}
	public void moveTo(PieceFile file, int rank) {
        super.moveTo(file, rank); 
        this.timesMoved++;
    }
}

class King extends ChessPiece {
	public King() {
		this.pieceType = PieceType.WK;
        this.pieceFile = PieceFile.b;
		this.pieceRank = 0;
        this.timesMoved = 0;
    }
	public boolean isValid(PieceFile file, int rank) {
		int vertical = rank - this.pieceRank; 
        int horizontal = file.ordinal()-this.pieceFile.ordinal(); 
	
	
		if(((vertical == 1 || vertical == -1) || (vertical == 0)) && ((horizontal==1 || horizontal ==-1) || (horizontal == 0))) {
			for (ReturnPiece[] row : Storage.storageBoard) {
				for (ReturnPiece returnPiece : row) {
					if(returnPiece != null) {
						ChessPiece CP = (ChessPiece)returnPiece;
						if((Storage.isWhite(this) && !(Storage.isWhite(CP))) || (Storage.isWhite(CP) && !(Storage.isWhite(this)))) {
							if(CP.isValid(file, rank)) {
								return false;
							}
						}
					}

				}
			}
			return true;
		}
		
		return false;
	}
	public void moveTo(PieceFile file, int rank) {
        
        Storage.storageBoard[pieceRank-1][pieceFile.ordinal()] = null;
		if(!(Storage.storageBoard[pieceRank-1][pieceFile.ordinal()] == null)) {
			ChessPiece killed = (ChessPiece)Storage.storageBoard[rank-1][file.ordinal()];                
			System.out.println("CHESSPIECE " + killed +  "WAS KILLED by: " + this);
			}
		Storage.storageBoard[rank-1][file.ordinal()] = this;
		this.pieceFile = file;
		this.pieceRank = rank;
        this.timesMoved++;
    }
}




class ReturnPlay {
	enum Message {ILLEGAL_MOVE, DRAW, 
				  RESIGN_BLACK_WINS, RESIGN_WHITE_WINS, 
				  CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS, 
				  STALEMATE};
	
	ArrayList<ReturnPiece> piecesOnBoard;
	Message message;
}
public class Chess2 { 
	
	enum Player { white, black }
	
	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
	public static ReturnPlay play(String move) {
	
		ReturnPlay ret = new ReturnPlay();
		if(move.equals("resign")) {  
			if(Storage.currPlayer == Player.white) {
				ret.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
				ret.piecesOnBoard = Storage.chessPiecesAL;
				return ret;
			} else {
				ret.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
				ret.piecesOnBoard = Storage.chessPiecesAL;
				return ret;
			}
		}
		String[] moves = move.split(" "); 
		char[] first = moves[0].strip().toCharArray();
		char[] second = moves[1].strip().toCharArray();
		String third;
		PieceFile start_file = Storage.fileMap.get(first[0]); 
		int start_rank = (int) first[1] - '0'; 

		PieceFile end_file = Storage.fileMap.get(second[0]); 
		int end_rank = (int) second[1] - '0'; 
		ChessPiece activePiece = (ChessPiece) Storage.storageBoard[start_rank - 1][start_file.ordinal()];
		if(activePiece == null) {
			ret.message = ReturnPlay.Message.ILLEGAL_MOVE;
			ret.piecesOnBoard = Storage.chessPiecesAL;
			System.out.println("No Piece at FileRank Inputted");
			return ret;
		}

		
		if(activePiece.pieceType == PieceType.WK) {
			if(activePiece.timesMoved == 0) {
				if((start_file == PieceFile.e && start_rank == 1) && (end_file == PieceFile.c && end_rank == 1)){ 
					ChessPiece rook = (ChessPiece) Storage.storageBoard[0][0];
					if(rook.timesMoved == 0){
						activePiece.moveTo(end_file, end_rank);
						rook.moveTo(PieceFile.d, 1);
						Storage.switchPlayer();
						if(Storage.isChecked()) {
							ret.message = ReturnPlay.Message.CHECK;
							ret.piecesOnBoard = Storage.chessPiecesAL;
						}
						if(Storage.CheckM8()) { 
							if(Storage.currPlayer == Player.white) {
								ret.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
								ret.piecesOnBoard = Storage.chessPiecesAL;
							} else {
								ret.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
								ret.piecesOnBoard = Storage.chessPiecesAL;
								
							}
						}
						ret.piecesOnBoard = Storage.chessPiecesAL;
						return ret;
					}
				}
				if((start_file == PieceFile.e && start_rank == 1) && (end_file == PieceFile.g && end_rank == 1)){ 
					ChessPiece rook = (ChessPiece) Storage.storageBoard[0][7];
					if(rook.timesMoved == 0){
						activePiece.moveTo(end_file, end_rank);
						rook.moveTo(PieceFile.f,1);
						Storage.switchPlayer();
						if(Storage.isChecked()) {
							ret.message = ReturnPlay.Message.CHECK;
							ret.piecesOnBoard = Storage.chessPiecesAL;
						}
						if(Storage.CheckM8()) { 
							if(Storage.currPlayer == Player.white) {
								ret.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
								ret.piecesOnBoard = Storage.chessPiecesAL;
							} else {
								ret.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
								ret.piecesOnBoard = Storage.chessPiecesAL;
								
							}
						}
						ret.piecesOnBoard = Storage.chessPiecesAL;
						return ret;
					}
				}
			}
		}
		if(activePiece.pieceType == PieceType.BK) {
			if (activePiece.timesMoved == 0) {
				if((start_file == PieceFile.e && start_rank == 8) && (end_file == PieceFile.c && end_rank == 8)) { 
					ChessPiece rook = (ChessPiece) Storage.storageBoard[7][0];
					if(rook.timesMoved == 0){
						activePiece.moveTo(end_file, end_rank);
						rook.moveTo(PieceFile.d,8);
						Storage.switchPlayer();
						if(Storage.isChecked()) {
							ret.message = ReturnPlay.Message.CHECK;
							ret.piecesOnBoard = Storage.chessPiecesAL;
						}
						if(Storage.CheckM8()) { 
							if(Storage.currPlayer == Player.white) {
								ret.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
								ret.piecesOnBoard = Storage.chessPiecesAL;
							} else {
								ret.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
								ret.piecesOnBoard = Storage.chessPiecesAL;
								
							}
						}
						ret.piecesOnBoard = Storage.chessPiecesAL;
						return ret;
					}
				}
				if((start_file == PieceFile.e && start_rank == 8) && (end_file == PieceFile.g && end_rank == 8)) { 
					ChessPiece rook = (ChessPiece) Storage.storageBoard[7][7];
					if(rook.timesMoved == 0){
						activePiece.moveTo(end_file, end_rank);
						rook.moveTo(PieceFile.f,8);
						Storage.switchPlayer();
						if(Storage.isChecked()) {
							ret.message = ReturnPlay.Message.CHECK;
							ret.piecesOnBoard = Storage.chessPiecesAL;
						}
						if(Storage.CheckM8()) { 
							if(Storage.currPlayer == Player.white) {
								ret.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
								ret.piecesOnBoard = Storage.chessPiecesAL;
							} else {
								ret.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
								ret.piecesOnBoard = Storage.chessPiecesAL;
								
							}
						}
						ret.piecesOnBoard = Storage.chessPiecesAL;
						return ret;
					}
				}
			}
		}
		
	
		Boolean pawnPromo = false;
		if(activePiece.pieceType == PieceType.WP || activePiece.pieceType == PieceType.BP) {
			if(Storage.isWhite(Storage.storageBoard[start_rank - 1][start_file.ordinal()]) && end_rank == 8) {
				pawnPromo = true;
			}
			if(!(Storage.isWhite(Storage.storageBoard[start_rank - 1][start_file.ordinal()])) && end_rank == 1) {
				pawnPromo = true;
			}
		}
		if(Storage.isChecked()) { 
			if((Storage.isWhite(Storage.storageBoard[start_rank-1][start_file.ordinal()]) && Storage.currPlayer == Player.white) || (!(Storage.isWhite(Storage.storageBoard[start_rank-1][start_file.ordinal()]) && Storage.currPlayer == Player.black))){
				if(Storage.quickSimulate(start_file, start_rank, end_file, end_rank)){
					if(activePiece.isValid(end_file, end_rank)) {
						activePiece.moveTo(end_file, end_rank);
						Storage.switchPlayer();
						if(Storage.isChecked()) {
							ret.message = ReturnPlay.Message.CHECK;
							ret.piecesOnBoard = Storage.chessPiecesAL;
						}
						if(Storage.CheckM8()) { 
							if(Storage.currPlayer == Player.white) {
								ret.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
								ret.piecesOnBoard = Storage.chessPiecesAL;
							} else {
								ret.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
								ret.piecesOnBoard = Storage.chessPiecesAL;
							}
						}
					} else {
						ret.message = ReturnPlay.Message.ILLEGAL_MOVE;
						ret.piecesOnBoard = Storage.chessPiecesAL;
					}
					} else {
						ret.message = ReturnPlay.Message.ILLEGAL_MOVE;
						ret.piecesOnBoard = Storage.chessPiecesAL;
					}
			} else {
				System.out.println("Piece Selected Doesn't Match Players Turn");
				ret.message = ReturnPlay.Message.ILLEGAL_MOVE;
				ret.piecesOnBoard = Storage.chessPiecesAL;
			} 
		} else {
			if((Storage.isWhite(Storage.storageBoard[start_rank-1][start_file.ordinal()]) && Storage.currPlayer == Player.white) || (!(Storage.isWhite(Storage.storageBoard[start_rank-1][start_file.ordinal()]) && Storage.currPlayer == Player.black))) {
				if(activePiece.isValid(end_file, end_rank)) {
					if(!(Storage.selfCheck(activePiece.pieceFile, activePiece.pieceRank, end_file, end_rank))) { 
						activePiece.moveTo(end_file, end_rank);
						
						Storage.switchPlayer();
						if(Storage.isChecked()) {
							ret.message = ReturnPlay.Message.CHECK;
							ret.piecesOnBoard = Storage.chessPiecesAL;
						}
						if(Storage.CheckM8()) { 
							if(Storage.currPlayer == Player.white) {
								ret.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
								ret.piecesOnBoard = Storage.chessPiecesAL;
							} else {
								ret.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
								ret.piecesOnBoard = Storage.chessPiecesAL;
							}

						}
					} else {
						ret.message = ReturnPlay.Message.ILLEGAL_MOVE;
						ret.piecesOnBoard = Storage.chessPiecesAL;
					}
					
				} else {
					ret.message = ReturnPlay.Message.ILLEGAL_MOVE;
					ret.piecesOnBoard = Storage.chessPiecesAL;
				}
			} else {
				System.out.println("Piece Selected Doesn't Match Players Turn");
				ret.message = ReturnPlay.Message.ILLEGAL_MOVE;
				ret.piecesOnBoard = Storage.chessPiecesAL;
			}
		}
		
		if(ret.message == ReturnPlay.Message.ILLEGAL_MOVE) {
			ret.piecesOnBoard = Storage.chessPiecesAL;
			return ret;
		}

		if(moves.length > 2) { 
			third = moves[2].strip();
			if(third.equals("draw?")) {
				ret.message = ReturnPlay.Message.DRAW;
				ret.piecesOnBoard = Storage.chessPiecesAL;
			}
			if(pawnPromo) {
				char c = third.charAt(0);
				Storage.pawnPromotion(Storage.storageBoard[end_rank-1][end_file.ordinal()], c);
				if(Storage.isChecked()) {
					ret.message = ReturnPlay.Message.CHECK;
					ret.piecesOnBoard = Storage.chessPiecesAL;
				}
				if(Storage.CheckM8()) { 
					if(Storage.currPlayer == Player.white) {
						ret.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
						ret.piecesOnBoard = Storage.chessPiecesAL;
					} else {
						ret.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
						ret.piecesOnBoard = Storage.chessPiecesAL;
					}

				}
			}
		} else{
			if(pawnPromo){
				Storage.pawnPromotion(Storage.storageBoard[end_rank-1][end_file.ordinal()], 'Q');

				if(Storage.isChecked()) {
					ret.message = ReturnPlay.Message.CHECK;
					ret.piecesOnBoard = Storage.chessPiecesAL;
				}
				if(Storage.CheckM8()) { 
					if(Storage.currPlayer == Player.white) {
						ret.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
						ret.piecesOnBoard = Storage.chessPiecesAL;
					} else {
						ret.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
						ret.piecesOnBoard = Storage.chessPiecesAL;
					}

				}
			}
		}
		
	
	
		ret.piecesOnBoard = Storage.chessPiecesAL;
		return ret;
	}
	/**
	 * The readInputs method below was implemented by us
	 
	public static void readInputs() {
		Scanner inputs = new Scanner(System.in);
		System.out.print("What's the move!?: "); 
		String line = inputs.nextLine();
		Scanner lineScan = new Scanner(line);
		int count = 0; 
		String word = lineScan.next();
        Storage.startpos = word;
    	if(lineScan.hasNext()) {word = lineScan.next(); Storage.endpos = word;} 
		if(lineScan.hasNext()) {word = lineScan.next(); Storage.thirdword = word;} 
		inputs.close();
        lineScan.close();
	}
	*/

	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
	

		
		
	Storage.currPlayer = Player.white;
	Storage.chessPiecesAL.clear();
	for (int i = 0; i < Storage.storageBoard.length; i++) {
        for (int j = 0; j < Storage.storageBoard.length; j++) {
            Storage.storageBoard[i][j] = null; 
        }
    }
	
	ReturnPiece WP1 = new Pawn(); 

	WP1.pieceType = PieceType.WP; 
	WP1.pieceFile = PieceFile.a; 
	WP1.pieceRank = 2;

	Storage.chessPiecesAL.add(WP1); 

	
	ReturnPiece WP2 = new Pawn(); 

	WP2.pieceType = PieceType.WP; 
	WP2.pieceFile = PieceFile.b; 
	WP2.pieceRank = 2;

	Storage.chessPiecesAL.add(WP2); 

	
	ReturnPiece WP3 = new Pawn(); 

	WP3.pieceType = PieceType.WP; 
	WP3.pieceFile = PieceFile.c; 
	WP3.pieceRank = 2;

	Storage.chessPiecesAL.add(WP3); 

	
	ReturnPiece WP4 = new Pawn(); 

	WP4.pieceType = PieceType.WP; 
	WP4.pieceFile = PieceFile.d; 
	WP4.pieceRank = 2;
	
	Storage.chessPiecesAL.add(WP4); 

	
	ReturnPiece WP5 = new Pawn(); 

	WP5.pieceType = PieceType.WP; 
	WP5.pieceFile = PieceFile.e; 
	WP5.pieceRank = 2;
				
	Storage.chessPiecesAL.add(WP5); 

	
	ReturnPiece WP6 = new Pawn(); 

	WP6.pieceType = PieceType.WP; 
	WP6.pieceFile = PieceFile.f; 
	WP6.pieceRank = 2;
							
	Storage.chessPiecesAL.add(WP6); 

	
	ReturnPiece WP7 = new Pawn(); 

	WP7.pieceType = PieceType.WP; 
	WP7.pieceFile = PieceFile.g; 
	WP7.pieceRank = 2;
										
	Storage.chessPiecesAL.add(WP7); 

	
	ReturnPiece WP8 = new Pawn(); 

	WP8.pieceType = PieceType.WP; 
	WP8.pieceFile = PieceFile.h; 
	WP8.pieceRank = 2;
													
	Storage.chessPiecesAL.add(WP8); 

	
	ReturnPiece BP1 = new Pawn(); 

	BP1.pieceType = PieceType.BP; 
	BP1.pieceFile = PieceFile.a; 
	BP1.pieceRank = 7;
;

	Storage.chessPiecesAL.add(BP1); 

	
	ReturnPiece BP2 = new Pawn(); 

	BP2.pieceType = PieceType.BP; 
	BP2.pieceFile = PieceFile.b; 
	BP2.pieceRank = 7;

	Storage.chessPiecesAL.add(BP2); 

	
	ReturnPiece BP3 = new Pawn(); 

	BP3.pieceType = PieceType.BP; 
	BP3.pieceFile = PieceFile.c; 
	BP3.pieceRank = 7;

	Storage.chessPiecesAL.add(BP3); 

	
	ReturnPiece BP4 = new Pawn(); 

	BP4.pieceType = PieceType.BP; 
	BP4.pieceFile = PieceFile.d; 
	BP4.pieceRank = 7;
		
	Storage.chessPiecesAL.add(BP4); 

	
	ReturnPiece BP5 = new Pawn(); 

	BP5.pieceType = PieceType.BP; 
	BP5.pieceFile = PieceFile.e; 
	BP5.pieceRank = 7;
					
	Storage.chessPiecesAL.add(BP5); 

	
	ReturnPiece BP6 = new Pawn(); 

	BP6.pieceType = PieceType.BP; 
	BP6.pieceFile = PieceFile.f; 
	BP6.pieceRank = 7;
								
	Storage.chessPiecesAL.add(BP6); 

	
	ReturnPiece BP7 = new Pawn(); 

	BP7.pieceType = PieceType.BP; 
	BP7.pieceFile = PieceFile.g; 
	BP7.pieceRank = 7;
											
	Storage.chessPiecesAL.add(BP7); 

	
	ReturnPiece BP8 = new Pawn(); 

	BP8.pieceType = PieceType.BP; 
	BP8.pieceFile = PieceFile.h; 	
	BP8.pieceRank = 7;
														
	Storage.chessPiecesAL.add(BP8);
			 
	
	ReturnPiece WR1 = new Rook(); 

	WR1.pieceType = PieceType.WR; 
	WR1.pieceFile = PieceFile.a; 
	WR1.pieceRank = 1;
														
	Storage.chessPiecesAL.add(WR1);

	
	ReturnPiece WR2 = new Rook(); 

	WR2.pieceType = PieceType.WR; 
	WR2.pieceFile = PieceFile.h; 
	WR2.pieceRank = 1;
														
	Storage.chessPiecesAL.add(WR2);

	
	ReturnPiece BR1 = new Rook(); 

	BR1.pieceType = PieceType.BR; 
	BR1.pieceFile = PieceFile.a; 
	BR1.pieceRank = 8;
														
	Storage.chessPiecesAL.add(BR1);

	
	ReturnPiece BR2 = new Rook(); 

	BR2.pieceType = PieceType.BR; 
	BR2.pieceFile = PieceFile.h; 
	BR2.pieceRank = 8;
														
	Storage.chessPiecesAL.add(BR2);

	
	ReturnPiece WK1 = new Knight(); 

	WK1.pieceType = PieceType.WN; 
	WK1.pieceFile = PieceFile.b; 
	WK1.pieceRank = 1;
														
	Storage.chessPiecesAL.add(WK1);

	
	ReturnPiece WK2 = new Knight(); 

	WK2.pieceType = PieceType.WN; 
	WK2.pieceFile = PieceFile.g; 
	WK2.pieceRank = 1;
														
	Storage.chessPiecesAL.add(WK2);

	
	ReturnPiece BK1 = new Knight(); 

	BK1.pieceType = PieceType.BN; 
	BK1.pieceFile = PieceFile.b; 
	BK1.pieceRank = 8;
														
	Storage.chessPiecesAL.add(BK1);
	
	ReturnPiece BK2 = new Knight(); 

	BK2.pieceType = PieceType.BN; 
	BK2.pieceFile = PieceFile.g; 
	BK2.pieceRank = 8;

	Storage.chessPiecesAL.add(BK2);

	
	ReturnPiece BB1 = new Bishop(); 

	BB1.pieceType = PieceType.BB; 
	BB1.pieceFile = PieceFile.c; 		
	BB1.pieceRank = 8;
															
	Storage.chessPiecesAL.add(BB1);

	
	ReturnPiece BB2 = new Bishop(); 
	
	BB2.pieceType = PieceType.BB; 
	BB2.pieceFile = PieceFile.f; 
	BB2.pieceRank = 8;

	Storage.chessPiecesAL.add(BB2);

	
	ReturnPiece WB1 = new Bishop(); 

	WB1.pieceType = PieceType.WB; 
	WB1.pieceFile = PieceFile.c; 		
	WB1.pieceRank = 1;
															
	Storage.chessPiecesAL.add(WB1);

	
	ReturnPiece WB2 = new Bishop(); 
	
	WB2.pieceType = PieceType.WB; 
	WB2.pieceFile = PieceFile.f; 
	WB2.pieceRank = 1;

	Storage.chessPiecesAL.add(WB2);

	
	ReturnPiece WQX = new Queen(); 

	WQX.pieceType = PieceType.WQ; 
	WQX.pieceFile = PieceFile.d; 		
	WQX.pieceRank = 1;
															
	Storage.chessPiecesAL.add(WQX);

	
	ReturnPiece BQX = new Queen(); 

	BQX.pieceType = PieceType.BQ; 
	BQX.pieceFile = PieceFile.d; 		
	BQX.pieceRank = 8;
															
	Storage.chessPiecesAL.add(BQX);

	
	ReturnPiece BKX = new King(); 

	BKX.pieceType = PieceType.BK; 
	BKX.pieceFile = PieceFile.e; 		
	BKX.pieceRank = 8;
															
	Storage.chessPiecesAL.add(BKX);

	
	ReturnPiece WKX = new King(); 

	WKX.pieceType = PieceType.WK; 
	WKX.pieceFile = PieceFile.e; 		
	WKX.pieceRank = 1;
															
	Storage.chessPiecesAL.add(WKX);
	
													
	System.out.println(Storage.chessPiecesAL);
	for(ReturnPiece piece : Storage.chessPiecesAL) {
		Storage.storageBoard[piece.pieceRank - 1][piece.pieceFile.ordinal()] = piece;
	}
	

		
		
	}
}
