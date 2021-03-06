// $ANTLR 2.7.6 (2005-12-22): "ASN1.G" -> "ASNLexer.java"$

package org.bn.compiler.parser;

import org.bn.compiler.parser.model.*;
import java.math.*;
import java.util.*;

import java.io.InputStream;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.TokenStreamRecognitionException;
import antlr.CharStreamException;
import antlr.CharStreamIOException;
import antlr.ANTLRException;
import java.io.Reader;
import java.util.Hashtable;
import antlr.CharScanner;
import antlr.InputBuffer;
import antlr.ByteBuffer;
import antlr.CharBuffer;
import antlr.Token;
import antlr.CommonToken;
import antlr.RecognitionException;
import antlr.NoViableAltForCharException;
import antlr.MismatchedCharException;
import antlr.TokenStream;
import antlr.ANTLRHashString;
import antlr.LexerSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.SemanticException;

public class ASNLexer extends antlr.CharScanner implements ASNTokenTypes, TokenStream
 {
public ASNLexer(InputStream in) {
	this(new ByteBuffer(in));
}
public ASNLexer(Reader in) {
	this(new CharBuffer(in));
}
public ASNLexer(InputBuffer ib) {
	this(new LexerSharedInputState(ib));
}
public ASNLexer(LexerSharedInputState state) {
	super(state);
	caseSensitiveLiterals = true;
	setCaseSensitive(true);
	literals = new Hashtable();
	literals.put(new ANTLRHashString("IDENTIFIER", this), new Integer(42));
	literals.put(new ANTLRHashString("PRESENT", this), new Integer(67));
	literals.put(new ANTLRHashString("MACRO", this), new Integer(146));
	literals.put(new ANTLRHashString("ENCRYPTED", this), new Integer(141));
	literals.put(new ANTLRHashString("CHOICE", this), new Integer(18));
	literals.put(new ANTLRHashString("ACCESS", this), new Integer(151));
	literals.put(new ANTLRHashString("ABSTRACT-BIND", this), new Integer(136));
	literals.put(new ANTLRHashString("END", this), new Integer(27));
	literals.put(new ANTLRHashString("DEFVAL", this), new Integer(156));
	literals.put(new ANTLRHashString("PDV", this), new Integer(65));
	literals.put(new ANTLRHashString("INTERSECTION", this), new Integer(49));
	literals.put(new ANTLRHashString("COMPONENT", this), new Integer(21));
	literals.put(new ANTLRHashString("STRING", this), new Integer(76));
	literals.put(new ANTLRHashString("PrintableString", this), new Integer(68));
	literals.put(new ANTLRHashString("CLASS", this), new Integer(19));
	literals.put(new ANTLRHashString("ARGUMENT", this), new Integer(8));
	literals.put(new ANTLRHashString("IA5String", this), new Integer(41));
	literals.put(new ANTLRHashString("ALGORITHM", this), new Integer(140));
	literals.put(new ANTLRHashString("EMBEDDED", this), new Integer(26));
	literals.put(new ANTLRHashString("SYNTAX", this), new Integer(150));
	literals.put(new ANTLRHashString("INSTANCE", this), new Integer(47));
	literals.put(new ANTLRHashString("ENUMERATED", this), new Integer(28));
	literals.put(new ANTLRHashString("NumericString", this), new Integer(56));
	literals.put(new ANTLRHashString("PLUSINFINITY", this), new Integer(66));
	literals.put(new ANTLRHashString("ABSTRACT-SYNTAX", this), new Integer(5));
	literals.put(new ANTLRHashString("EXTENSION-ATTRIBUTE", this), new Integer(130));
	literals.put(new ANTLRHashString("TAGS", this), new Integer(77));
	literals.put(new ANTLRHashString("ERRORS", this), new Integer(30));
	literals.put(new ANTLRHashString("UTF8String", this), new Integer(86));
	literals.put(new ANTLRHashString("FROM", this), new Integer(37));
	literals.put(new ANTLRHashString("NULL", this), new Integer(55));
	literals.put(new ANTLRHashString("REAL", this), new Integer(70));
	literals.put(new ANTLRHashString("GraphicString", this), new Integer(40));
	literals.put(new ANTLRHashString("UTCTime", this), new Integer(85));
	literals.put(new ANTLRHashString("SIGNATURE", this), new Integer(143));
	literals.put(new ANTLRHashString("TOKEN", this), new Integer(131));
	literals.put(new ANTLRHashString("UNION", this), new Integer(81));
	literals.put(new ANTLRHashString("AUTOMATIC", this), new Integer(10));
	literals.put(new ANTLRHashString("UNBIND", this), new Integer(125));
	literals.put(new ANTLRHashString("OPTIONAL", this), new Integer(63));
	literals.put(new ANTLRHashString("SIGNED", this), new Integer(142));
	literals.put(new ANTLRHashString("EXTENSIONS", this), new Integer(129));
	literals.put(new ANTLRHashString("SET", this), new Integer(74));
	literals.put(new ANTLRHashString("ObjectDescriptor", this), new Integer(57));
	literals.put(new ANTLRHashString("WITH", this), new Integer(89));
	literals.put(new ANTLRHashString("OF", this), new Integer(61));
	literals.put(new ANTLRHashString("ABSTRACT-UNBIND", this), new Integer(137));
	literals.put(new ANTLRHashString("INDEX", this), new Integer(155));
	literals.put(new ANTLRHashString("EXPORTS", this), new Integer(33));
	literals.put(new ANTLRHashString("FALSE", this), new Integer(36));
	literals.put(new ANTLRHashString("GeneralizedTime", this), new Integer(38));
	literals.put(new ANTLRHashString("SECURITY-CATEGORY", this), new Integer(133));
	literals.put(new ANTLRHashString("UNIQUE", this), new Integer(82));
	literals.put(new ANTLRHashString("VideotexString", this), new Integer(87));
	literals.put(new ANTLRHashString("BY", this), new Integer(16));
	literals.put(new ANTLRHashString("BASEDNUM", this), new Integer(11));
	literals.put(new ANTLRHashString("TYPE-IDENTIFIER", this), new Integer(80));
	literals.put(new ANTLRHashString("PRIVATE", this), new Integer(69));
	literals.put(new ANTLRHashString("ANY", this), new Integer(7));
	literals.put(new ANTLRHashString("DEFAULT", this), new Integer(23));
	literals.put(new ANTLRHashString("OBJECT", this), new Integer(58));
	literals.put(new ANTLRHashString("BMPString", this), new Integer(14));
	literals.put(new ANTLRHashString("MIN", this), new Integer(54));
	literals.put(new ANTLRHashString("APPLICATION-SERVICE-ELEMENT", this), new Integer(126));
	literals.put(new ANTLRHashString("INCLUDES", this), new Integer(46));
	literals.put(new ANTLRHashString("REFERENCE", this), new Integer(154));
	literals.put(new ANTLRHashString("RELATIVE", this), new Integer(71));
	literals.put(new ANTLRHashString("BOOLEAN", this), new Integer(15));
	literals.put(new ANTLRHashString("ALL", this), new Integer(6));
	literals.put(new ANTLRHashString("PROTECTED", this), new Integer(144));
	literals.put(new ANTLRHashString("DEFINED", this), new Integer(24));
	literals.put(new ANTLRHashString("CONSTRAINED", this), new Integer(22));
	literals.put(new ANTLRHashString("IMPLIED", this), new Integer(44));
	literals.put(new ANTLRHashString("RESULT", this), new Integer(72));
	literals.put(new ANTLRHashString("VisibleString", this), new Integer(88));
	literals.put(new ANTLRHashString("CHARACTER", this), new Integer(17));
	literals.put(new ANTLRHashString("TOKEN-DATA", this), new Integer(132));
	literals.put(new ANTLRHashString("BEGIN", this), new Integer(12));
	literals.put(new ANTLRHashString("BIT", this), new Integer(13));
	literals.put(new ANTLRHashString("ISO646String", this), new Integer(50));
	literals.put(new ANTLRHashString("ERROR", this), new Integer(29));
	literals.put(new ANTLRHashString("BIND", this), new Integer(124));
	literals.put(new ANTLRHashString("APPLICATION-CONTEXT", this), new Integer(127));
	literals.put(new ANTLRHashString("SIZE", this), new Integer(75));
	literals.put(new ANTLRHashString("EXTERNAL", this), new Integer(35));
	literals.put(new ANTLRHashString("PORT", this), new Integer(134));
	literals.put(new ANTLRHashString("ABSENT", this), new Integer(4));
	literals.put(new ANTLRHashString("OBJECT-TYPE", this), new Integer(145));
	literals.put(new ANTLRHashString("TeletexString", this), new Integer(78));
	literals.put(new ANTLRHashString("PARAMETER", this), new Integer(64));
	literals.put(new ANTLRHashString("OPERATION", this), new Integer(60));
	literals.put(new ANTLRHashString("ABSTRACT-ERROR", this), new Integer(139));
	literals.put(new ANTLRHashString("STATUS", this), new Integer(152));
	literals.put(new ANTLRHashString("OID", this), new Integer(62));
	literals.put(new ANTLRHashString("EXCEPT", this), new Integer(31));
	literals.put(new ANTLRHashString("APPLICATION", this), new Integer(9));
	literals.put(new ANTLRHashString("MAX", this), new Integer(52));
	literals.put(new ANTLRHashString("EXTENSION", this), new Integer(128));
	literals.put(new ANTLRHashString("MINUSINFINITY", this), new Integer(53));
	literals.put(new ANTLRHashString("GeneralString", this), new Integer(39));
	literals.put(new ANTLRHashString("LINKED", this), new Integer(51));
	literals.put(new ANTLRHashString("IMPORTS", this), new Integer(45));
	literals.put(new ANTLRHashString("UNIVERSAL", this), new Integer(83));
	literals.put(new ANTLRHashString("REFINE", this), new Integer(135));
	literals.put(new ANTLRHashString("OCTET", this), new Integer(59));
	literals.put(new ANTLRHashString("COMPONENTS", this), new Integer(20));
	literals.put(new ANTLRHashString("DEFINITIONS", this), new Integer(25));
	literals.put(new ANTLRHashString("DESCRIPTION", this), new Integer(153));
	literals.put(new ANTLRHashString("TRUE", this), new Integer(79));
	literals.put(new ANTLRHashString("ABSTRACT-OPERATION", this), new Integer(138));
	literals.put(new ANTLRHashString("SEQUENCE", this), new Integer(73));
	literals.put(new ANTLRHashString("UniversalString", this), new Integer(84));
	literals.put(new ANTLRHashString("IMPLICIT", this), new Integer(43));
	literals.put(new ANTLRHashString("INTEGER", this), new Integer(48));
	literals.put(new ANTLRHashString("EXTENSIBILITY", this), new Integer(34));
	literals.put(new ANTLRHashString("EXPLICIT", this), new Integer(32));
}

public Token nextToken() throws TokenStreamException {
	Token theRetToken=null;
tryAgain:
	for (;;) {
		Token _token = null;
		int _ttype = Token.INVALID_TYPE;
		resetText();
		try {   // for char stream error handling
			try {   // for lexical error handling
				switch ( LA(1)) {
				case '|':
				{
					mBAR(true);
					theRetToken=_returnToken;
					break;
				}
				case ',':
				{
					mCOMMA(true);
					theRetToken=_returnToken;
					break;
				}
				case '!':
				{
					mEXCLAMATION(true);
					theRetToken=_returnToken;
					break;
				}
				case '^':
				{
					mINTERSECTION(true);
					theRetToken=_returnToken;
					break;
				}
				case '<':
				{
					mLESS(true);
					theRetToken=_returnToken;
					break;
				}
				case '{':
				{
					mL_BRACE(true);
					theRetToken=_returnToken;
					break;
				}
				case '[':
				{
					mL_BRACKET(true);
					theRetToken=_returnToken;
					break;
				}
				case '(':
				{
					mL_PAREN(true);
					theRetToken=_returnToken;
					break;
				}
				case '+':
				{
					mPLUS(true);
					theRetToken=_returnToken;
					break;
				}
				case '}':
				{
					mR_BRACE(true);
					theRetToken=_returnToken;
					break;
				}
				case ']':
				{
					mR_BRACKET(true);
					theRetToken=_returnToken;
					break;
				}
				case ')':
				{
					mR_PAREN(true);
					theRetToken=_returnToken;
					break;
				}
				case ';':
				{
					mSEMI(true);
					theRetToken=_returnToken;
					break;
				}
				case '\t':  case '\n':  case '\u000c':  case '\r':
				case ' ':
				{
					mWS(true);
					theRetToken=_returnToken;
					break;
				}
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				case '8':  case '9':
				{
					mNUMBER(true);
					theRetToken=_returnToken;
					break;
				}
				case 'A':  case 'B':  case 'C':  case 'D':
				case 'E':  case 'F':  case 'G':  case 'H':
				case 'I':  case 'J':  case 'K':  case 'L':
				case 'M':  case 'N':  case 'O':  case 'P':
				case 'Q':  case 'R':  case 'S':  case 'T':
				case 'U':  case 'V':  case 'W':  case 'X':
				case 'Y':  case 'Z':
				{
					mUPPER(true);
					theRetToken=_returnToken;
					break;
				}
				case 'a':  case 'b':  case 'c':  case 'd':
				case 'e':  case 'f':  case 'g':  case 'h':
				case 'i':  case 'j':  case 'k':  case 'l':
				case 'm':  case 'n':  case 'o':  case 'p':
				case 'q':  case 'r':  case 's':  case 't':
				case 'u':  case 'v':  case 'w':  case 'x':
				case 'y':  case 'z':
				{
					mLOWER(true);
					theRetToken=_returnToken;
					break;
				}
				case '"':
				{
					mC_STRING(true);
					theRetToken=_returnToken;
					break;
				}
				default:
					if ((LA(1)=='.') && (LA(2)=='.') && (LA(3)=='.')) {
						mELLIPSIS(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='-') && (LA(2)=='-') && ((LA(3) >= '\u0003' && LA(3) <= '\u00ff'))) {
						mSL_COMMENT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='\'') && (_tokenSet_0.member(LA(2))) && (_tokenSet_1.member(LA(3)))) {
						mB_OR_H_STRING(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)==':') && (LA(2)==':')) {
						mASSIGN_OP(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='-') && (LA(2)=='-') && (true)) {
						mCOMMENT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='.') && (LA(2)=='.') && (true)) {
						mDOTDOT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='\'') && (LA(2)=='B') && (true)) {
						mCHARB(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='\'') && (LA(2)=='H')) {
						mCHARH(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)==':') && (true)) {
						mCOLON(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='.') && (true)) {
						mDOT(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='-') && (true)) {
						mMINUS(true);
						theRetToken=_returnToken;
					}
					else if ((LA(1)=='\'') && (true)) {
						mSINGLE_QUOTE(true);
						theRetToken=_returnToken;
					}
				else {
					if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);}
				else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
				}
				}
				if ( _returnToken==null ) continue tryAgain; // found SKIP token
				_ttype = _returnToken.getType();
				_ttype = testLiteralsTable(_ttype);
				_returnToken.setType(_ttype);
				return _returnToken;
			}
			catch (RecognitionException e) {
				throw new TokenStreamRecognitionException(e);
			}
		}
		catch (CharStreamException cse) {
			if ( cse instanceof CharStreamIOException ) {
				throw new TokenStreamIOException(((CharStreamIOException)cse).io);
			}
			else {
				throw new TokenStreamException(cse.getMessage());
			}
		}
	}
}

	public final void mASSIGN_OP(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ASSIGN_OP;
		int _saveIndex;
		
		match("::=");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mBAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BAR;
		int _saveIndex;
		
		match('|');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COLON;
		int _saveIndex;
		
		match(':');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COMMA;
		int _saveIndex;
		
		match(',');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCOMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = COMMENT;
		int _saveIndex;
		
		match("--");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DOT;
		int _saveIndex;
		
		match('.');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mDOTDOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = DOTDOT;
		int _saveIndex;
		
		match("..");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mELLIPSIS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = ELLIPSIS;
		int _saveIndex;
		
		match("...");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mEXCLAMATION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = EXCLAMATION;
		int _saveIndex;
		
		match('!');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mINTERSECTION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = INTERSECTION;
		int _saveIndex;
		
		match('^');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLESS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LESS;
		int _saveIndex;
		
		match('<');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mL_BRACE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = L_BRACE;
		int _saveIndex;
		
		match('{');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mL_BRACKET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = L_BRACKET;
		int _saveIndex;
		
		match('[');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mL_PAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = L_PAREN;
		int _saveIndex;
		
		match('(');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mMINUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = MINUS;
		int _saveIndex;
		
		match('-');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mPLUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = PLUS;
		int _saveIndex;
		
		match('+');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mR_BRACE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = R_BRACE;
		int _saveIndex;
		
		match('}');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mR_BRACKET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = R_BRACKET;
		int _saveIndex;
		
		match(']');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mR_PAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = R_PAREN;
		int _saveIndex;
		
		match(')');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSEMI(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SEMI;
		int _saveIndex;
		
		match(';');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSINGLE_QUOTE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SINGLE_QUOTE;
		int _saveIndex;
		
		match("'");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCHARB(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CHARB;
		int _saveIndex;
		
		match("'B");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mCHARH(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = CHARH;
		int _saveIndex;
		
		match("'H");
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = WS;
		int _saveIndex;
		
		{
		int _cnt27=0;
		_loop27:
		do {
			switch ( LA(1)) {
			case ' ':
			{
				match(' ');
				break;
			}
			case '\t':
			{
				match('\t');
				break;
			}
			case '\u000c':
			{
				match('\f');
				break;
			}
			case '\n':  case '\r':
			{
				{
				if ((LA(1)=='\r') && (LA(2)=='\n') && (true) && (true) && (true) && (true) && (true) && (true) && (true) && (true) && (true)) {
					match("\r\n");
					if ( inputState.guessing==0 ) {
						newline();
					}
				}
				else if ((LA(1)=='\r') && (true) && (true) && (true) && (true) && (true) && (true) && (true) && (true) && (true) && (true)) {
					match('\r');
					if ( inputState.guessing==0 ) {
						newline();
					}
				}
				else if ((LA(1)=='\n')) {
					match('\n');
					if ( inputState.guessing==0 ) {
						newline();
					}
				}
				else {
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				
				}
				break;
			}
			default:
			{
				if ( _cnt27>=1 ) { break _loop27; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			}
			_cnt27++;
		} while (true);
		}
		if ( inputState.guessing==0 ) {
			_ttype = Token.SKIP;
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mSL_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = SL_COMMENT;
		int _saveIndex;
		
		{
		mCOMMENT(false);
		{
		_loop32:
		do {
			if (((LA(1)=='-') && ((LA(2) >= '\u0003' && LA(2) <= '\u00ff')) && (true) && (true) && (true) && (true) && (true) && (true) && (true) && (true) && (true))&&( LA(2)!='-' )) {
				match('-');
			}
			else if ((_tokenSet_2.member(LA(1)))) {
				{
				match(_tokenSet_2);
				}
			}
			else {
				break _loop32;
			}
			
		} while (true);
		}
		{
		switch ( LA(1)) {
		case '\n':  case '\r':
		{
			{
			{
			switch ( LA(1)) {
			case '\r':
			{
				match('\r');
				break;
			}
			case '\n':
			{
				break;
			}
			default:
			{
				throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
			}
			}
			}
			match('\n');
			}
			if ( inputState.guessing==0 ) {
				newline();
			}
			break;
		}
		case '-':
		{
			mCOMMENT(false);
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		}
		if ( inputState.guessing==0 ) {
			_ttype = Token.SKIP;
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mNUMBER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = NUMBER;
		int _saveIndex;
		
		{
		int _cnt38=0;
		_loop38:
		do {
			if (((LA(1) >= '0' && LA(1) <= '9'))) {
				matchRange('0','9');
			}
			else {
				if ( _cnt38>=1 ) { break _loop38; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			
			_cnt38++;
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mUPPER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = UPPER;
		int _saveIndex;
		
		{
		matchRange('A','Z');
		}
		{
		_loop43:
		do {
			if ((_tokenSet_3.member(LA(1))) && (true) && (true) && (true) && (true) && (true) && (true) && (true) && (true) && (true) && (true)) {
				{
				switch ( LA(1)) {
				case 'a':  case 'b':  case 'c':  case 'd':
				case 'e':  case 'f':  case 'g':  case 'h':
				case 'i':  case 'j':  case 'k':  case 'l':
				case 'm':  case 'n':  case 'o':  case 'p':
				case 'q':  case 'r':  case 's':  case 't':
				case 'u':  case 'v':  case 'w':  case 'x':
				case 'y':  case 'z':
				{
					matchRange('a','z');
					break;
				}
				case 'A':  case 'B':  case 'C':  case 'D':
				case 'E':  case 'F':  case 'G':  case 'H':
				case 'I':  case 'J':  case 'K':  case 'L':
				case 'M':  case 'N':  case 'O':  case 'P':
				case 'Q':  case 'R':  case 'S':  case 'T':
				case 'U':  case 'V':  case 'W':  case 'X':
				case 'Y':  case 'Z':
				{
					matchRange('A','Z');
					break;
				}
				case '-':
				{
					match('-');
					break;
				}
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				case '8':  case '9':
				{
					matchRange('0','9');
					break;
				}
				case '_':
				{
					match('_');
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
			}
			else {
				break _loop43;
			}
			
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mLOWER(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = LOWER;
		int _saveIndex;
		
		{
		matchRange('a','z');
		}
		{
		_loop48:
		do {
			if ((_tokenSet_3.member(LA(1))) && (true) && (true) && (true) && (true) && (true) && (true) && (true) && (true) && (true) && (true)) {
				{
				switch ( LA(1)) {
				case 'a':  case 'b':  case 'c':  case 'd':
				case 'e':  case 'f':  case 'g':  case 'h':
				case 'i':  case 'j':  case 'k':  case 'l':
				case 'm':  case 'n':  case 'o':  case 'p':
				case 'q':  case 'r':  case 's':  case 't':
				case 'u':  case 'v':  case 'w':  case 'x':
				case 'y':  case 'z':
				{
					matchRange('a','z');
					break;
				}
				case 'A':  case 'B':  case 'C':  case 'D':
				case 'E':  case 'F':  case 'G':  case 'H':
				case 'I':  case 'J':  case 'K':  case 'L':
				case 'M':  case 'N':  case 'O':  case 'P':
				case 'Q':  case 'R':  case 'S':  case 'T':
				case 'U':  case 'V':  case 'W':  case 'X':
				case 'Y':  case 'Z':
				{
					matchRange('A','Z');
					break;
				}
				case '-':
				{
					match('-');
					break;
				}
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				case '8':  case '9':
				{
					matchRange('0','9');
					break;
				}
				case '_':
				{
					match('_');
					break;
				}
				default:
				{
					throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
				}
				}
				}
			}
			else {
				break _loop48;
			}
			
		} while (true);
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mBDIG(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = BDIG;
		int _saveIndex;
		
		{
		switch ( LA(1)) {
		case '0':
		{
			match('0');
			break;
		}
		case '1':
		{
			match('1');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mHDIG(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = HDIG;
		int _saveIndex;
		
		switch ( LA(1)) {
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			{
			{
			matchRange('0','9');
			}
			}
			break;
		}
		case 'A':  case 'B':  case 'C':  case 'D':
		case 'E':  case 'F':
		{
			{
			matchRange('A','F');
			}
			break;
		}
		case 'a':  case 'b':  case 'c':  case 'd':
		case 'e':  case 'f':
		{
			{
			matchRange('a','f');
			}
			break;
		}
		default:
		{
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mB_OR_H_STRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = B_OR_H_STRING;
		int _saveIndex;
		
		{
		boolean synPredMatched59 = false;
		if (((LA(1)=='\'') && (LA(2)=='0'||LA(2)=='1') && (_tokenSet_4.member(LA(3))) && (_tokenSet_5.member(LA(4))) && (true) && (true) && (true) && (true) && (true) && (true) && (true))) {
			int _m59 = mark();
			synPredMatched59 = true;
			inputState.guessing++;
			try {
				{
				mB_STRING(false);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched59 = false;
			}
			rewind(_m59);
inputState.guessing--;
		}
		if ( synPredMatched59 ) {
			mB_STRING(false);
			if ( inputState.guessing==0 ) {
				_ttype = B_STRING;
			}
		}
		else if ((LA(1)=='\'') && (_tokenSet_0.member(LA(2))) && (_tokenSet_1.member(LA(3))) && (_tokenSet_6.member(LA(4))) && (true) && (true) && (true) && (true) && (true) && (true) && (true)) {
			mH_STRING(false);
			if ( inputState.guessing==0 ) {
				_ttype = H_STRING;
			}
		}
		else {
			throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());
		}
		
		}
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mB_STRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = B_STRING;
		int _saveIndex;
		
		mSINGLE_QUOTE(false);
		{
		int _cnt62=0;
		_loop62:
		do {
			if ((LA(1)=='0'||LA(1)=='1')) {
				mBDIG(false);
			}
			else {
				if ( _cnt62>=1 ) { break _loop62; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			
			_cnt62++;
		} while (true);
		}
		mSINGLE_QUOTE(false);
		match('B');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	protected final void mH_STRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = H_STRING;
		int _saveIndex;
		
		mSINGLE_QUOTE(false);
		{
		int _cnt65=0;
		_loop65:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				mHDIG(false);
			}
			else {
				if ( _cnt65>=1 ) { break _loop65; } else {throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());}
			}
			
			_cnt65++;
		} while (true);
		}
		mSINGLE_QUOTE(false);
		match('H');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	public final void mC_STRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
		int _ttype; Token _token=null; int _begin=text.length();
		_ttype = C_STRING;
		int _saveIndex;
		
		match('"');
		{
		_loop68:
		do {
			switch ( LA(1)) {
			case 'A':  case 'B':  case 'C':  case 'D':
			case 'E':  case 'F':  case 'G':  case 'H':
			case 'I':  case 'J':  case 'K':  case 'L':
			case 'M':  case 'N':  case 'O':  case 'P':
			case 'Q':  case 'R':  case 'S':  case 'T':
			case 'U':  case 'V':  case 'W':  case 'X':
			case 'Y':  case 'Z':
			{
				mUPPER(false);
				break;
			}
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':  case 'g':  case 'h':
			case 'i':  case 'j':  case 'k':  case 'l':
			case 'm':  case 'n':  case 'o':  case 'p':
			case 'q':  case 'r':  case 's':  case 't':
			case 'u':  case 'v':  case 'w':  case 'x':
			case 'y':  case 'z':
			{
				mLOWER(false);
				break;
			}
			default:
			{
				break _loop68;
			}
			}
		} while (true);
		}
		match('"');
		if ( _createToken && _token==null && _ttype!=Token.SKIP ) {
			_token = makeToken(_ttype);
			_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));
		}
		_returnToken = _token;
	}
	
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 287948901175001088L, 541165879422L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 287949450930814976L, 541165879422L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = new long[8];
		data[0]=-35184372098056L;
		for (int i = 1; i<=3; i++) { data[i]=-1L; }
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 287984085547089920L, 576460745995190270L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 844974685945856L, 0L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 844974685945856L, 4L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 287949450930814976L, 541165879678L, 0L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	
	}
